# sjm-oauth-resource-server

get Data Using Token
GET http://localhost:8080/spring-security-oauth-resource/foos/15
Error: connect ECONNREFUSED 127.0.0.1:8080
Request Headers
Accept: application/json, text/plain, */*
Accept-Encoding: gzip, deflate
Accept-Language: en-US,en;q=0.9
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJqb2huIiwic2NvcGUiOlsiZm9vIiwicmVhZCIsIndyaXRlIl0sIm9yZ2FuaXphdGlvbiI6ImpvaG5TaUtSIiwiZXhwIjoxNTk2OTU2OTM0LCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiZTg2MjFmNmQtYWExMi00OGYxLTliMTEtY2Y1ODgyOTBiNzJiIiwiY2xpZW50X2lkIjoiZm9vQ2xpZW50SWRQYXNzd29yZCJ9.2LfmwwB9LlLn1kBZXtweS7hAJxWEpqnn1WXdquzXttY
Cache-Control: no-cache
User-Agent: PostmanRuntime/7.26.2
Postman-Token: 3fc22022-1e17-458a-b422-aa1ffc454033
Host: localhost:8080
Connection: keep-alive


get Refresh Token
POST http://localhost:8080/oauth/token?grant_type=refresh_token&scope=read&refresh_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJqb2huIiwic2NvcGUiOlsiZm9vIiwicmVhZCIsIndyaXRlIl0sIm9yZ2FuaXphdGlvbiI6ImpvaG5qdk1VIiwiYXRpIjoiOTMzMThiYWQtZjljYS00ZDU1LWJiOTQtMWI5YWFmNGQ4YTllIiwiZXhwIjoxNTk5NjQwMjU3LCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiMmE2ZDI4MmUtYzI4NS00YzVhLTg5NDgtYWNhMWNkZDcwNjc0IiwiY2xpZW50X2lkIjoiZm9vQ2xpZW50SWRQYXNzd29yZCJ9.wA2RWIHKPpncIlWdlHpszGdcDNRhNgxN4O5XIjvoOTg
Error: connect ECONNREFUSED 127.0.0.1:8080
Request Headers
Authorization: Basic Zm9vQ2xpZW50SWRQYXNzd29yZDpzZWNyZXQ=
Cache-Control: no-cache
Content-Type: application/x-www-form-urlencoded
User-Agent: PostmanRuntime/7.26.2
Accept: */*
Postman-Token: 035bcad5-7acb-4e48-9e05-a5317bc469a0
Host: localhost:8080
Accept-Encoding: gzip, deflate, br
Connection: keep-alive
