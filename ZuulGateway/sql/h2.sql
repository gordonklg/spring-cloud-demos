create table B_APPLICATION
(
  ID                   IDENTITY             primary key,
  NAME                 VARCHAR(30),
  CLIENT_ID            VARCHAR(100),
  CLIENT_SECRET        VARCHAR(100)
);
comment on table B_APPLICATION is '接入方应用程序，网关外部系统';
comment on column B_APPLICATION.ID is '主键';
comment on column B_APPLICATION.NAME is '名称';
comment on column B_APPLICATION.CLIENT_ID is 'Client ID';
comment on column B_APPLICATION.CLIENT_SECRET is 'CLIENT_SECRET';



create table B_SERVICE
(
  ID                   IDENTITY             primary key,
  NAME                 VARCHAR(100),
  SYSTEM               VARCHAR(100),
  REQ_URL              VARCHAR(300),
  REQ_METHOD           VARCHAR(50),
  BACKEND_SYSTEM_TYPE char(1)              default '1' not null,
  BACKEND_URL         VARCHAR(200),
  BACKEND_METHOD      VARCHAR(50),
  SERVICE_ID           VARCHAR(100),
  TIMEOUT              INT                  default 10000 not null
);
comment on table B_SERVICE is '服务表';
comment on column B_SERVICE.ID is '主键';
comment on column B_SERVICE.NAME is '名称';
comment on column B_SERVICE.SYSTEM is '所属系统';
comment on column B_SERVICE.REQ_URL is '请求URL';
comment on column B_SERVICE.REQ_METHOD is '请求方法';
comment on column B_SERVICE.BACKEND_SYSTEM_TYPE is '后端系统类型，1=NORMAL，2=SC，默认为1';
comment on column B_SERVICE.BACKEND_URL is '后端服务URL';
comment on column B_SERVICE.BACKEND_METHOD is '后端服务方法';
comment on column B_SERVICE.SERVICE_ID is '服务ID';
comment on column B_SERVICE.TIMEOUT is '默认超时时间（毫秒）';



create table R_APP_SERVICE
(
  ID                   IDENTITY             primary key,
  APP_ID               BIGINT,
  SERVICE_ID           BIGINT
);
comment on table R_APP_SERVICE is 'APP和SERVICE对应关系';
comment on column R_APP_SERVICE.ID is '主键';
comment on column R_APP_SERVICE.APP_ID is 'APP ID';
comment on column R_APP_SERVICE.SERVICE_ID is 'SERVICE_ID';






