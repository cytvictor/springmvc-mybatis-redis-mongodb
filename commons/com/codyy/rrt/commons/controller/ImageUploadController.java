package com.codyy.rrt.commons.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.DispatcherServlet;

import com.alibaba.fastjson.JSONObject;
import com.codyy.base.web.SpringContext;
import com.codyy.rrt.commons.entity.AppConfig;
import com.codyy.rrt.commons.entity.DomainConfig;
import com.codyy.rrt.commons.utils.ThumbnailImageTransfer;
import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

@SuppressWarnings("serial")
@Controller
@RequestMapping("/upload/")
public class ImageUploadController extends DispatcherServlet{
	
	private static final Log logger = LogFactory.getLog(ImageUploadController.class);
	
	/**
	 * 上传图片
	 * @throws IOException 
	 * @throws Exception 
	 */
	@RequestMapping(value = "image", method = RequestMethod.POST)
	public void upload(HttpServletRequest request, HttpServletResponse response, Writer writer) throws IOException{
		JSONObject json = new JSONObject();
        //resolver upload
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setDefaultEncoding("UTF-8");
		resolver.setServletContext(request.getSession().getServletContext());
		resolver.setMaxInMemorySize(1024*1024);
		MultipartHttpServletRequest multiRequest = resolver.resolveMultipart(request);
		Map<String, MultipartFile> filemap = multiRequest.getFileMap();
		if (filemap.size() == 0){
			json.put("status", false);
			writer.write(json.toString());
			return;
		}
		String thumb = multiRequest.getParameter("thumb");
		
		//save file
		MongoTemplate template = SpringContext.getBean(MongoTemplate.class);
		MultipartFile multiFile = filemap.values().iterator().next();
		String originalFilename = multiFile.getOriginalFilename().toLowerCase();
		InputStream in = multiFile.getInputStream();
		DB db = template.getDb();
		GridFS gfs = new GridFS(db, "images");
		String filename = createFilename(gfs, originalFilename);
		GridFSInputFile file = gfs.createFile(in, filename);
		file.save();
		json.put("name", filename);
		DomainConfig config = SpringContext.getBean(DomainConfig.class);
		json.put("fullName", config.getPublicServer() + "/images/" + filename);
		
		//save thumb file
		if (StringUtils.isNotBlank(thumb)){
			String suffix = null;
			if (StringUtils.isNotBlank(filename)){
				int p = filename.lastIndexOf('.');
				if ( p >= 0 ){
					suffix = filename.substring(p).toLowerCase();
				}
			}
			if (StringUtils.isNotBlank(suffix)){
				String thumbFilename = "THUMB_" + filename;
				ThumbnailImageTransfer transfer = new ThumbnailImageTransfer();
				String width = multiRequest.getParameter("width");//如果指定了width和height参数，则thumb按照width和height参数获取
				String height = multiRequest.getParameter("height");
				if(multiFile.getSize()>1024*1024){
					GridFSDBFile gff=gfs.findOne((ObjectId) file.getId());
					in = gff.getInputStream();
				}else{
					in.reset();//保存图片时已经读取了InputStream,需要重设文件指针
				}
				try {
					if (StringUtils.isBlank(width) || StringUtils.isBlank(height)){
						GridFSInputFile fileThumb = gfs.createFile(transfer.getImageInputStreamByResizeFix(150, 150, in, suffix), thumbFilename);
						fileThumb.save();
					} else {
						Integer w = Integer.valueOf(width);
						Integer h = Integer.valueOf(height);
						GridFSInputFile fileThumb = gfs.createFile(transfer.getImageInputStreamByResizeFix(w, h, in, suffix), thumbFilename);
						fileThumb.save();
					}
				} catch (Exception e) {
					json.put("status", false);
					writer.write(json.toString());
					return;
				}
				json.put("thumb", thumbFilename);
				json.put("fullThumb", config.getPublicServer() + "/images/" + thumbFilename);
			}
		}
		json.put("status", true);
		
		writer.write(json.toString());
		logger.debug("transferTo over");
	}
	
	//构建随机的文件名
	private static String createFilename(GridFS gfs, String filename){
		String suffix = "";
		if (StringUtils.isNotBlank(filename)){
			int p = filename.lastIndexOf('.');
			if ( p >= 0 ){
				suffix = filename.substring(p);
			}
		}
		String randFilename = UUID.randomUUID().toString() + suffix;
		GridFSDBFile file = gfs.findOne(randFilename);
		if (file == null){
			logger.debug("generate random image filename:" + randFilename);
			return randFilename;
		} else 
			return createFilename(gfs, filename);
	}
	
	/**
	 * 后台调用保存图片文件
	 * @param input
	 * @param originalFilename
	 * @return
	 */
	public static String saveImageBackground(InputStream input, String originalFilename){
		MongoTemplate template = SpringContext.getBean(MongoTemplate.class);
		DB db = template.getDb();
		GridFS gfs = new GridFS(db, "images");
		String filename = createFilename(gfs, originalFilename);
		GridFSInputFile file = gfs.createFile(input, filename);
		file.save();
		return filename;
	}
	
	/**
	 * 上传普通附件
	 * @param request
	 * @param response
	 * @param writer
	 * @throws Exception
	 */
	@RequestMapping(value = "attached", method = RequestMethod.POST)
	public void attached(HttpServletRequest request, HttpServletResponse response, Writer writer) throws Exception {
		JSONObject json = new JSONObject();
        //resolver upload
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setDefaultEncoding("UTF-8");
		resolver.setServletContext(request.getSession().getServletContext());
		resolver.setMaxInMemorySize(1024*1024);
		MultipartHttpServletRequest multiRequest = resolver.resolveMultipart(request);
		Map<String, MultipartFile> filemap = multiRequest.getFileMap();
		if (filemap.size() == 0){
			json.put("status", false);
			writer.write(json.toString());
			return;
		}
		
		//save file
		MongoTemplate template = SpringContext.getBean(MongoTemplate.class);
		MultipartFile multiFile = filemap.values().iterator().next();
		String originalFilename = multiFile.getOriginalFilename();
		InputStream in = multiFile.getInputStream();
		DB db = template.getDb();
		GridFS gfs = new GridFS(db, "attached");
		String filename = createFilename(gfs, originalFilename.toLowerCase());
		GridFSInputFile file = gfs.createFile(in, filename);
		file.put("original", originalFilename);
		file.save();
		json.put("original", originalFilename);
		json.put("size", file.getLength());
		json.put("name", filename);
		json.put("status", true);
		
		writer.write(json.toString());
		logger.debug("transferTo over");
	}
	
	@RequestMapping(value = "watermark/uploadWatermark", method = RequestMethod.POST)
	public void uploadWatermark(HttpServletRequest request, HttpServletResponse response, Writer writer) throws IOException{
		JSONObject json = new JSONObject();
		//resolver upload
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setDefaultEncoding("UTF-8");
		resolver.setServletContext(request.getSession().getServletContext());
		resolver.setMaxInMemorySize(1024*1024);
		MultipartHttpServletRequest multiRequest = resolver.resolveMultipart(request);
		String apk = multiRequest.getParameter("apk");
		Map<String, MultipartFile> filemap = multiRequest.getFileMap();
		if (filemap.size() == 0){
			json.put("status", false);
			writer.write(json.toString());
			return;
		}
		
		MultipartFile multiFile = filemap.values().iterator().next();
		InputStream in = multiFile.getInputStream();
		AppConfig config = SpringContext.getBean(AppConfig.class);
		File file = null;
		if (StringUtils.isBlank(apk)) {
			file = new File(config.getUploadfolder()+File.separator+"watermark.png");
		} else {
			file = new File(config.getUploadfolder()+File.separator+"RRTParents_temp.apk");
		}
		
		try {
			file = createWatermarkFile(file);
			FileOutputStream out = new FileOutputStream(file);
			StreamUtils.copy(in, out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			json.put("status", false);
			writer.write(json.toString());
			return;
		}
		
		json.put("status", true);
		writer.write(json.toString());
		logger.debug("transferTo over");
	}
	
	private File createWatermarkFile(File file) throws Exception{
		if (file.exists()) {
			file.delete();
			if (file.createNewFile()) {
				return file;
			} else {
				return createWatermarkFile(file);
			}
		} else {
			if (file.createNewFile()) {
				return file;
			} else {
				return createWatermarkFile(file);
			}
		}
	}
	
	@RequestMapping("watermark/{watermark}")
	public void showWatermark(@PathVariable String watermark,HttpServletRequest request,HttpServletResponse response) throws Exception{
		AppConfig config = SpringContext.getBean(AppConfig.class);
		File file = new File(config.getUploadfolder()+File.separator+watermark+".png");
		if (file.exists()) {
			InputStream input = new  FileInputStream(file);
			StreamUtils.copy(input, response.getOutputStream());
			input.close();
		}
	}
}