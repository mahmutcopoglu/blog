package com.mahmutcopoglu.blog.util;

public final class ApiPaths {
    private static final String BASE_PATH = "/api";

    public static final class UserCtrl{
        public static final String CTRL = BASE_PATH + "/users";
    }

    public static final class PostCtrl{
        public static final String CTRL = BASE_PATH + "/posts";
    }

    public static final class PostCommentCtrl{
        public static final String CTRL = BASE_PATH + "/postcomments";
    }

    public static final class CategoryCtrl{
        public static final String CTRL = BASE_PATH + "/categories";
    }

    public static final class TagCtrl{
        public static final String CTRL = BASE_PATH + "/tags";
    }

    public static final class RoleCtrl{
        public static final String CTRL = BASE_PATH + "/roles";
    }
}
