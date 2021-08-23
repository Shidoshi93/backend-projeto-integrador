package Api.constants;

public class SecurityConstants {
    public static final String SIGN_UP_URL = "/user/save";
    public static final String SIGN_UP_ADDRESS = "/address/save";
    public static final String GET_POSTS = "/post/listAll";
    public static final String GET_POSTS_PAGE = "/post/listAll/page";
    public static final String[] AUTH_LIST = {
            // -- swagger ui
            "**/swagger-resources/**",
            "/swagger-ui.html",
            "/webjars/**",
    };

    public static final String KEY = "q3t6w9z$C&F)J@NcQfTjWnZr4u7x!A%D*G-KaPdSgUkXp2s5v8y/B?E(H+MbQeTh";
    public static final String HEADER_NAME = "Authorization";
    public static final Long EXPIRATION_TIME =  100000L * 60 * 30;
}
