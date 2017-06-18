package app.auth;

import app.config.DbSnapshotHolder;
import app.model.DbSnapshot;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Ionut on 21-May-17.
 */
@Component("SnapshotFilter")
public class SnapshotFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String snapshotKey = httpRequest.getHeader("Snapshot");
        DbSnapshot snapshot = snapshotKey != null ? new DbSnapshot(snapshotKey) : DbSnapshotHolder.getDefaultSnapshot();
        DbSnapshotHolder.setDbSnapshot(snapshot);
        try {
            chain.doFilter(request, response);
        } finally {
            DbSnapshotHolder.clearDbSnapshot();
        }
    }

    @Override
    public void destroy() {

    }

}