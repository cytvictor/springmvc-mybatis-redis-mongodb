package com.codyy.rrt.commons.service;

import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;

@Service
public class FileManageService {
	@Autowired
	private MongoTemplate mongoTemplate ;
	private GridFS getGridFS(String bucketName){
		DB db=mongoTemplate.getDb();
		return (new GridFS(db, bucketName));
	}
	
	
	public void deleteImage(String imageName) {
		GridFS gfs=getGridFS("images");
		gfs.remove(imageName);
	}
	
	public void deleteImageBatch(Collection<String> imageNames) {
		if(CollectionUtils.isNotEmpty(imageNames)){
			GridFS gfs=getGridFS("images");
			Criteria c = Criteria.where("filename").in(imageNames);
			Query query = new Query(c);
			gfs.remove(query.getQueryObject());
		}
	}
	
	public void deleteAttacheFile(String attachFileName) {
		GridFS gfs=getGridFS("attached");
		gfs.remove(attachFileName);
	}
	
	public void deleteAttacheFileBatch(Collection<String> attachFileNames) {
		if(CollectionUtils.isNotEmpty(attachFileNames)){
			GridFS gfs=getGridFS("attached");
			Criteria c = Criteria.where("filename").in(attachFileNames);
			Query query = new Query(c);
			gfs.remove(query.getQueryObject());
		}
	}
}
