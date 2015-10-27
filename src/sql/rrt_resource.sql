/*==============================================================*/
/* Table: RESOURCES                                             */
/*==============================================================*/
create table RESOURCES 
(
   RESOURCE_ID          VARCHAR2(50 BYTE)    not null,
   RESOURCE_SOURCE      VARCHAR2(20 BYTE),
   RESOURCE_PRICE       NUMBER(10,3),
   TRANSFER_FLAG        VARCHAR2(10 BYTE),
   BASE_USER_ID         VARCHAR2(50 BYTE),
   BASE_SEMESTER_ID     VARCHAR2(50 BYTE),
   BASE_CLASSLEVEL_ID   VARCHAR2(50 BYTE),
   BASE_DISCIPLINE_ID   VARCHAR2(50 BYTE),
   BASE_VERSION_ID      VARCHAR2(50 BYTE),
   BASE_FASCICLE_ID     VARCHAR2(50 BYTE),
   BASE_CHAPTER_ID      VARCHAR2(50 BYTE),
   BASE_PART_ID         VARCHAR2(50 BYTE),
   RESOURCE_CATALOG_FIRST_ID VARCHAR2(50 BYTE),
   RESOURCE_CATALOG_SECOND_ID VARCHAR2(50 BYTE),
   DELETE_BASE_USER_ID  VARCHAR2(50 BYTE),
   RESOURCE_NAME        VARCHAR2(200 BYTE),
   RESOURCE_COLUMN_ID        VARCHAR2(50 BYTE),
   RESOURCE_COLUMN_TYPE VARCHAR2(50 BYTE),
   DESCRIPTION          VARCHAR2(500 BYTE),
   THUMB                VARCHAR2(200 BYTE),
   HIGH_DEFINE          VARCHAR2(200 BYTE),
   NORMAL_DEFINE        VARCHAR2(200 BYTE),
   DURATION             NUMBER(10),
   STUDY_RESOURCE       VARCHAR2(200 BYTE),
   STUDY_RESOURCE_TYPE  VARCHAR2(50 BYTE),
   STUDY_RESOURCE_SIZE  NUMBER(19),
   COMMENT_CLOSED       VARCHAR2(50 BYTE),
   CREATE_TIME          TIMESTAMP(6),
   DELETE_FLAG          VARCHAR2(50 BYTE),
   DELETE_TIME          TIMESTAMP(6),
   FUN_TYPE             VARCHAR2(50 BYTE),
   EVALUATE             NUMBER(10),
   EVALUATE_AVG         NUMBER(10,1),
   EVALUATE_COUNT       NUMBER(10),
   REVIEW_FLAG          VARCHAR2(50 BYTE),
   TRANS_FLAG           VARCHAR2(50 BYTE),
   TRANS_PAGE_COUNT     NUMBER(10),
   FUN_CONTENT          VARCHAR2(4000 BYTE),
   HOLD_TYPE            VARCHAR2(50 BYTE),
   HOLD_ID              VARCHAR2(50 BYTE),
   ORG_SOURCE           VARCHAR2(50 BYTE),
   COMMENT_COUNT        NUMBER(10),
   DOWNLOAD_COUNT       NUMBER(10),
   VIEW_COUNT           NUMBER(10),
   FAVORITE_COUNT       NUMBER(10),
   AUTHOR               VARCHAR2(50 BYTE),
   constraint PK_RESOURCES primary key (RESOURCE_ID)
);

comment on column RESOURCES.RESOURCE_ID is
'资源主键';

comment on column RESOURCES.RESOURCE_SOURCE is
'资源来源：体制内或运营商';

comment on column RESOURCES.RESOURCE_PRICE is
'资源价格';

comment on column RESOURCES.TRANSFER_FLAG is
'是否转载';

comment on column RESOURCES.BASE_USER_ID is
'发布者ID';

comment on column RESOURCES.BASE_SEMESTER_ID is
'学段ID';

comment on column RESOURCES.BASE_CLASSLEVEL_ID is
'年级ID';

comment on column RESOURCES.BASE_DISCIPLINE_ID is
'学科ID';

comment on column RESOURCES.BASE_VERSION_ID is
'版本Id';

comment on column RESOURCES.BASE_FASCICLE_ID is
'分册Id';

comment on column RESOURCES.BASE_CHAPTER_ID is
'章Id';

comment on column RESOURCES.BASE_PART_ID is
'节Id';

comment on column RESOURCES.RESOURCE_CATALOG_FIRST_ID is
'一级资源分类Id';

comment on column RESOURCES.RESOURCE_CATALOG_SECOND_ID is
'二级资源分类Id';

comment on column RESOURCES.DELETE_BASE_USER_ID is
'删除资源的用户ID';

comment on column RESOURCES.RESOURCE_NAME is
'资源名称';

comment on column RESOURCES.RESOURCE_COLUMN_ID is
'资源类型';

comment on column RESOURCES.RESOURCE_COLUMN_TYPE is
'资源栏目类型';

comment on column RESOURCES.DESCRIPTION is
'资源描述';

comment on column RESOURCES.THUMB is
'缩略图';

comment on column RESOURCES.HIGH_DEFINE is
'高清附件';

comment on column RESOURCES.NORMAL_DEFINE is
'普清附件';

comment on column RESOURCES.DURATION is
'视频时长';

comment on column RESOURCES.STUDY_RESOURCE is
'教学资源';

comment on column RESOURCES.STUDY_RESOURCE_TYPE is
'教学资源类型';

comment on column RESOURCES.STUDY_RESOURCE_SIZE is
'教学资源大小';

comment on column RESOURCES.CREATE_TIME is
'创建时间';

comment on column RESOURCES.DELETE_FLAG is
'删除状态';

comment on column RESOURCES.DELETE_TIME is
'删除时间';

comment on column RESOURCES.FUN_TYPE is
'轻松一刻分类';

comment on column RESOURCES.TRANS_FLAG is
'资源转换标示';

comment on column RESOURCES.FUN_CONTENT is
'轻松一刻文本类型内容';

comment on column RESOURCES.HOLD_TYPE is
'持有者类型';

comment on column RESOURCES.HOLD_ID is
'资源持有者';

comment on column RESOURCES.ORG_SOURCE is
'资源的来源机构';

comment on column RESOURCES.COMMENT_COUNT is
'评论次数';

comment on column RESOURCES.DOWNLOAD_COUNT is
'下载次数';

comment on column RESOURCES.VIEW_COUNT is
'观看次数';

comment on column RESOURCES.FAVORITE_COUNT is
'收藏次数';

comment on column RESOURCES.AUTHOR is
'资源作者';

/*==============================================================*/
/* Index: INDEX_RESOURCES_USERID                                */
/*==============================================================*/
create index INDEX_RESOURCES_USERID on RESOURCES (
   BASE_USER_ID ASC
);

/*==============================================================*/
/* Table: RESOURCE_KNOWLEDGE                                    */
/*==============================================================*/
create table RESOURCE_KNOWLEDGE 
(
   RESOURCE_KNOWLEDGE_ID VARCHAR2(50 BYTE)    not null,
   RESOURCE_ID          VARCHAR2(50 BYTE),
   BASE_SEMESTER_ID     VARCHAR2(50 BYTE),
   BASE_DISCIPLINE_ID   VARCHAR2(50 BYTE),
   BASE_KNOWLEDGE_ID VARCHAR2(50 BYTE),
   FIRST_BASE_KNOWLEDGE_ID VARCHAR2(50 BYTE),
   SECOND_BASE_KNOWLEDGE_ID VARCHAR2(50 BYTE),
   THIRD_BASE_KNOWLEDGE_ID VARCHAR2(50 BYTE),
   FOURTH_BASE_KNOWLEDGE_ID VARCHAR2(50 BYTE),
   FIFTH_BASE_KNOWLEDGE_ID VARCHAR2(50 BYTE),
   SIXTH_BASE_KNOWLEDGE_ID VARCHAR2(50 BYTE),
   constraint PK_RESOURCE_KNOWLEDGE primary key (RESOURCE_KNOWLEDGE_ID)
);

comment on table RESOURCE_KNOWLEDGE is
'资源知识点表';

comment on column RESOURCE_KNOWLEDGE.RESOURCE_ID is
'资源ID';

comment on column RESOURCE_KNOWLEDGE.BASE_SEMESTER_ID is
'学段ID';

comment on column RESOURCE_KNOWLEDGE.BASE_DISCIPLINE_ID is
'学科ID';

comment on column RESOURCE_KNOWLEDGE.BASE_KNOWLEDGE_ID is
'最后一级知识点ID';

comment on column RESOURCE_KNOWLEDGE.FIRST_BASE_KNOWLEDGE_ID is
'一级知识点ID';

comment on column RESOURCE_KNOWLEDGE.SECOND_BASE_KNOWLEDGE_ID is
'二级知识点ID';

comment on column RESOURCE_KNOWLEDGE.THIRD_BASE_KNOWLEDGE_ID is
'三级知识点ID';

comment on column RESOURCE_KNOWLEDGE.FOURTH_BASE_KNOWLEDGE_ID is
'四级知识点ID';

comment on column RESOURCE_KNOWLEDGE.FIFTH_BASE_KNOWLEDGE_ID is
'五级知识点ID';

comment on column RESOURCE_KNOWLEDGE.SIXTH_BASE_KNOWLEDGE_ID is
'六级知识点ID';

/*==============================================================*/
/* Index: INDEX_RK_RESOURCE_ID                                  */
/*==============================================================*/
create index INDEX_RK_RESOURCE_ID on RESOURCE_KNOWLEDGE (
   RESOURCE_ID ASC
);

alter table RESOURCE_KNOWLEDGE
   add constraint FK_RK_RESOURCE_ID foreign key (RESOURCE_ID)
      references RESOURCES (RESOURCE_ID);

      
/*==============================================================*/
/* Table: RESOURCE_LABEL                                        */
/*==============================================================*/
create table RESOURCE_LABEL 
(
   RESOURCE_LABEL_ID    VARCHAR2(50 BYTE)    not null,
   RESOURCE_LABEL_NAME  VARCHAR2(50 BYTE),
   constraint PK_RESOURCE_LABEL primary key (RESOURCE_LABEL_ID)
);

comment on table RESOURCE_LABEL is
'资源标签';

comment on column RESOURCE_LABEL.RESOURCE_LABEL_ID is
'资源标签ID';

comment on column RESOURCE_LABEL.RESOURCE_LABEL_NAME is
'资源标签名称';


/*==============================================================*/
/* Table: RESOURCE_LABEL_RESOURCES                              */
/*==============================================================*/
create table RESOURCE_LABEL_RESOURCES 
(
   RESOURCE_LABEL_RESOURCES_ID VARCHAR2(50 BYTE)    not null,
   RESOURCE_LABEL_ID    VARCHAR2(50 BYTE),
   RESOURCE_ID          VARCHAR2(50 BYTE),
   constraint PK_RESOURCE_LABEL_RESOURCES primary key (RESOURCE_LABEL_RESOURCES_ID)
);

comment on table RESOURCE_LABEL_RESOURCES is
'资源标签与资源关系表';

comment on column RESOURCE_LABEL_RESOURCES.RESOURCE_LABEL_ID is
'资源标签ID';

comment on column RESOURCE_LABEL_RESOURCES.RESOURCE_ID is
'资源ID';

/*==============================================================*/
/* Index: INDEX_RLR_RLID                                        */
/*==============================================================*/
create index INDEX_RLR_RLID on RESOURCE_LABEL_RESOURCES (
   RESOURCE_LABEL_ID ASC
);

/*==============================================================*/
/* Index: INDEX_RLR_RID                                         */
/*==============================================================*/
create index INDEX_RLR_RID on RESOURCE_LABEL_RESOURCES (
   RESOURCE_ID ASC
);

alter table RESOURCE_LABEL_RESOURCES
   add constraint FK_RLR_RID foreign key (RESOURCE_ID)
      references RESOURCES (RESOURCE_ID);

alter table RESOURCE_LABEL_RESOURCES
   add constraint FK_RLR_RL_ID foreign key (RESOURCE_LABEL_ID)
      references RESOURCE_LABEL (RESOURCE_LABEL_ID);
      
      
/*==============================================================*/
/* Table: RESOURCE_IMAGE                                        */
/*==============================================================*/
create table RESOURCE_IMAGE 
(
   RESOURCE_IMAGE_ID    VARCHAR2(50 BYTE)    not null,
   RESOURCE_ID          VARCHAR2(50 BYTE),
   IMAGE_NAME           VARCHAR2(50 BYTE),
   constraint PK_RESOURCE_IMAGE primary key (RESOURCE_IMAGE_ID)
);

comment on table RESOURCE_IMAGE is
'资源图片表';

comment on column RESOURCE_IMAGE.RESOURCE_IMAGE_ID is
'主键';

comment on column RESOURCE_IMAGE.RESOURCE_ID is
'资源ID';

comment on column RESOURCE_IMAGE.IMAGE_NAME is
'资源图片名称';

/*==============================================================*/
/* Index: INDEX_RI_RESOURCE_ID                                  */
/*==============================================================*/
create index INDEX_RI_RESOURCE_ID on RESOURCE_IMAGE (
   RESOURCE_ID ASC
);

alter table RESOURCE_IMAGE
   add constraint FK_RI_REFERENCE_RESOURCE foreign key (RESOURCE_ID)
      references RESOURCES (RESOURCE_ID);
