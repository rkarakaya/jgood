
it is multi build gradle project written in java and angularjs. 

Main areas:
 - MongoDB 3.0
 - Spring
 - Spring Security
 - MVC based rest implementation
 - webjars
 

TODOs:
- Add JSR303 validation
- Exception handling needs to be more granular.
- Move in-memory authentication & authorization to db.
- Test cases is not complete yet.
- Integrate ESAPI
- User signup
- Integrate growl 
  .
  .	  
  and many more:-(


NOTEs:
- Two in-memory users exist.
	withUser("admin@jgood.com").password("12345").roles("ADMIN")
	withUser("user@jgood.com").password("12345").roles("USER")
- During development, I used Wildfly 8.2.Final and Nginx locally	
	
  

My development nginx conf was this:

server {
    listen       80;
    server_name  jgood-dev.com;

	location /{
		root   F:/workspace_co/jgood/modules/web/src/main/webapp/;
		index index_dev.html
		expires           0;
		add_header        Cache-Control private;
	}
	
	location /api {
		proxy_set_header   X-Real-IP $remote_addr;
		proxy_set_header   Host      $http_host;
		proxy_pass         http://127.0.0.1:8080;
		add_header         X-Robots-Tag noindex always;
	}

    error_page   500 502 503 504  /50x.html;

}


My production nginx conf was this:

server {
    listen       80;
    server_name  jgood-prod.com;

	location /{
		root   F:/tools/program_files/nginx-1.11.3/jgood/;
		index index.html
		expires           0;
		add_header        Cache-Control private;
	}
	
	location /api {
		proxy_set_header   X-Real-IP $remote_addr;
		proxy_set_header   Host      $http_host;
		proxy_pass         http://127.0.0.1:8080;
		add_header         X-Robots-Tag noindex always;
	}

    error_page   500 502 503 504  /50x.html;

}