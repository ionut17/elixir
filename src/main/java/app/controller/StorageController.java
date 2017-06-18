package app.controller;

import app.config.DbSnapshotHolder;
import app.controller.common.BaseController;
import app.exceptions.ErrorMessagesWrapper;
import app.exceptions.JwtAuthenticationException;
import app.exceptions.StorageFileNotFoundException;
import app.model.DbSnapshot;
import app.model.activity.Activity;
import app.model.activity.ActivityFile;
import app.model.dto.ResourceDto;
import app.model.user.Student;
import app.model.user.User;
import app.repository.StudentRepository;
import app.service.*;
import app.service.activity.ActivityFileService;
import app.service.activity.ActivityService;
import app.service.storage.StorageService;
//import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import app.service.user.LecturerService;
import app.service.user.StudentService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.tools.ant.taskdefs.condition.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

//import javax.servlet.ServletContext;
//import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

//import org.apache.catalina.servlet4preview.http.HttpServletRequest;

@RestController
public class StorageController extends BaseController {

    @Autowired
    private StorageService storageService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private ActivityFileService activityFileService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private AuthDetailsService authDetailsService;
    @Autowired
    StudentRepository students;
    @Autowired
    StudentService studentService;
    @Autowired
    LecturerService lecturerService;
    @Autowired
    CourseService courseService;
    @Autowired
    GroupService groupService;
    @Autowired
    TimeConverter timeConverter;

    @RequestMapping(value = "/storage/retrieve/{file_id}", params = {"k"}, method = RequestMethod.GET)
    public @ResponseBody ResponseEntity retrieveFile(@PathVariable("file_id") long fileId, @RequestParam("k") String key, UriComponentsBuilder ucBuilder, HttpServletRequest request, HttpServletResponse response) throws IOException{
        String snapshotKey = request.getHeader("Snapshot");
        DbSnapshot snapshot = snapshotKey != null ? new DbSnapshot(snapshotKey) : DbSnapshotHolder.getDefaultSnapshot();
        User user;
        try{
            user = jwtService.verifyToken(key);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorMessagesWrapper(emp.NOT_AUTHORIZED), HttpStatus.BAD_REQUEST);
        }
        if (user==null){
            return new ResponseEntity<>(new ErrorMessagesWrapper(emp.NOT_AUTHORIZED), HttpStatus.BAD_REQUEST);
        }

        ServletContext context = request.getServletContext();
        ActivityFile activityFile = activityFileService.findById(fileId);
        //TODO let lecturer to access student's files who are enrolled in his courses
        if (!user.getEmail().equals(activityFile.getStudent().getEmail()) && user.getType().equals("student")){
            return new ResponseEntity<>(new ErrorMessagesWrapper(emp.NOT_AUTHORIZED), HttpStatus.BAD_REQUEST);
        }

        ResourceDto resourceDto = new ResourceDto(activityFile, snapshot);

        if (!resourceDto.isValid()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Path filePath = storageService.load(resourceDto.toRelativePath());
        if (filePath == null){
            return new ResponseEntity<>(new ErrorMessagesWrapper(emp.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
        File file = filePath.toFile();

        FileInputStream inputStream = new FileInputStream(file);
        // Get MIME type
        String mimeTypeValue = context.getMimeType(filePath.toString());
        if (mimeTypeValue == null) {
            mimeTypeValue = "application/octet-stream";
        }
        String contentDispositionValue = String.format("attachment; filename=\"%s\"", file.getName());

        byte[] output = org.apache.commons.io.IOUtils.toByteArray(inputStream);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", contentDispositionValue);
        headers.setContentType(MediaType.parseMediaType(mimeTypeValue));
        headers.setContentLength(file.length());
        return new ResponseEntity<>(output, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/storage/upload", headers=("content-type=multipart/*"), method = RequestMethod.POST)
    public ResponseEntity<Resource> uploadFile(@RequestParam(value = "file") MultipartFile uploadedFile, @RequestParam(value="activityId") Long activityId, @RequestParam(value="fileName") String fileName, UriComponentsBuilder ucBuilder, HttpServletRequest request) {
        String snapshotKey = request.getHeader("Snapshot");
        DbSnapshot snapshot = snapshotKey != null ? new DbSnapshot(snapshotKey) : DbSnapshotHolder.getDefaultSnapshot();
        if (uploadedFile.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //Get target activity and logged in user
        Activity activity = activityService.findById(activityId);
        if (activity == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Student student = students.findOne(authDetailsService.getAuthenticatedUser().getId());
        if (student == null || !student.getCourses().contains(activity.getCourse())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); //check rights
        }
        //Create file activity
        ActivityFile activityFile = new ActivityFile();
        activityFile.setActivity(activity);
        activityFile.setStudent(student);
        activityFile.setFileName(fileName);
        activityFile.setExtension(FilenameUtils.getExtension(uploadedFile.getOriginalFilename()));
        activityFile.setUploadDate(new Date());
        activityFile.setUploadDate(timeConverter.addMinutes(new Date(), 180)); //convert to GMT+3 time
        ResourceDto uploadedResource = new ResourceDto(activityFile, snapshot);
        if (!uploadedResource.isValid()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Path stored = storageService.storeAt(uploadedFile, uploadedResource.toRelativePath());
        //If file exists add ActivityFile
        ActivityFile storedActivity = null;
        if (stored.toFile().exists()){
            storedActivity = activityFileService.add(activityFile);
        }
        if (storedActivity == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/files/file/{file_id}").buildAndExpand(storedActivity.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    //Import
    @RequestMapping(value = "/import/{type}", headers=("content-type=multipart/*"), method = RequestMethod.POST)
    public ResponseEntity<Resource> importData(@RequestParam(value = "file") MultipartFile uploadedFile, @PathVariable("type") String importType, UriComponentsBuilder ucBuilder) {
        if (uploadedFile.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            File importedFile = new File(storageService.getTempDir(), uploadedFile.getOriginalFilename());
            FileUtils.writeByteArrayToFile(importedFile, uploadedFile.getBytes());

            //Call the service
            switch(importType){
                case "students":
                    studentService.importEntities(importedFile);
                    break;
                case "lecturers":
                    lecturerService.importEntities(importedFile);
                    break;
                case "courses":
                    courseService.importEntities(importedFile);
                    break;
                case "groups":
                    groupService.importEntities(importedFile);
                    break;
                case "courses_ownerships":
                    courseService.importCourseOwnerships(importedFile);
                case "courses_attendants":
                    courseService.importCourseAttendants(importedFile);
                case "groups_students":
                    groupService.importGroupStudents(importedFile);
                default:
                    break;
            }

            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}