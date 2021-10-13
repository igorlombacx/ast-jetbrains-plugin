package com.checkmarx.intellij.commands;

import com.checkmarx.ast.wrapper.CxConfig;
import com.checkmarx.ast.wrapper.CxException;
import com.checkmarx.intellij.settings.global.CxWrapperFactory;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

/**
 * Handle scan related operations with the wrapper
 */
public class Scan {

    /**
     * Get latest scan id, independent of project.
     *
     * @return scan id
     */
    @NotNull
    public static String getLatestScanId() throws
            CxConfig.InvalidCLIConfigException,
            IOException,
            URISyntaxException,
            CxException,
            InterruptedException {
        return CxWrapperFactory.build().scanList().get(0).getID();
    }

    /**
     * Get global scan list, for every project.
     *
     * @return global scan list
     */
    @NotNull
    public static List<com.checkmarx.ast.scan.Scan> getList()
            throws
            IOException,
            URISyntaxException,
            InterruptedException,
            CxConfig.InvalidCLIConfigException,
            CxException {

        return CxWrapperFactory.build().scanList("limit=10000");
    }

    /**
     * Get scan list for a specific project.
     *
     * @param projectId id for project
     * @return scan list for project
     */
    @NotNull
    public static List<com.checkmarx.ast.scan.Scan> getList(String projectId)
            throws
            IOException,
            URISyntaxException,
            InterruptedException,
            CxConfig.InvalidCLIConfigException,
            CxException {

        return CxWrapperFactory.build().scanList(String.format("project-id=%s,limit=10000", projectId));
    }

    /**
     * Get scan info by scan id
     *
     * @param scanId scan id
     * @return scan object
     */
    public static com.checkmarx.ast.scan.Scan scanShow(String scanId)
            throws
            IOException,
            URISyntaxException,
            InterruptedException,
            CxConfig.InvalidCLIConfigException,
            CxException {
        return CxWrapperFactory.build().scanShow(UUID.fromString(scanId));
    }
}
