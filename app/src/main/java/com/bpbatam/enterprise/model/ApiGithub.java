package com.bpbatam.enterprise.model;

/**
 * Created by User on 20/07/2016.
 */
public class ApiGithub {
    /*"current_user_url": "https://api.github.com/user",
            "current_user_authorizations_html_url": "https://github.com/settings/connections/applications{/client_id}",
            "authorizations_url": "https://api.github.com/authorizations",
            "code_search_url": "https://api.github.com/search/code?q={query}{&page,per_page,sort,order}",
            "emails_url": "https://api.github.com/user/emails",
            "emojis_url": "https://api.github.com/emojis",
            "events_url": "https://api.github.com/events",
            "feeds_url": "https://api.github.com/feeds",
            "followers_url": "https://api.github.com/user/followers",
            "following_url": "https://api.github.com/user/following{/target}",*/

    public class Results{
        public String current_user_url;
        public String current_user_authorizations_html_url;
        public String authorizations_url;
        public String code_search_url;
        public String emails_url;
        public String emojis_url;
        public String events_url;
        public String feeds_url;
        public String followers_url;
        public String following_url;
    }

    public class Links
    {
        public String self ;
        public String prev ;
        public String next ;
        public String first ;
        public String last ;
    }

    public class Meta
    {
        public double benchmark ;
        public int current_page ;
        public int total_page ;
        public int limit ;
        public int total_row ;
    }

    public class Server
    {
        public String REQUEST_METHOD ;
        public String CONTENT_TYPE ;
        public String REMOTE_ADDR ;
        public String HTTP_USER_AGENT ;
    }

    public Messages messages ;
    public Results results ;
    public Meta _meta ;
    public Server _server ;
    public Links _links ;
    public String language ;

}
