/**
 * 
 */
package com.globerry.project.domain;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * @author Artem
 *
 */
public class UploadItem
{
    private String name;
    private CommonsMultipartFile fileData;
   
    public String getName()
    {
      return name;
    }
   
    public void setName(String name)
    {
      this.name = name;
    }
   
    public CommonsMultipartFile getFileData()
    {
      return fileData;
    }
   
    public void setFileData(CommonsMultipartFile fileData)
    {
      this.fileData = fileData;
    }
    @Override
    public boolean equals(Object obj)
    {
	if(obj == null) return false;
	if(!(obj instanceof UploadItem)) return false;
	UploadItem item = (UploadItem) obj;
	
	if(this.name == null ^ item.getName() == null) return false;
	if(!((this.name == null && item.getName() == null) || this.name.equals(item.getName()))) return false;
	
	if(this.fileData == null ^ item.getFileData() == null) return false;
	if(!((this.fileData == null && item.getFileData() == null) || this.fileData.equals(item.getFileData()))) return false;
	return true;
    }
    @Override
    public int hashCode()
    {
	int result = 15;
	result = 3*result + (name == null ? 0 : name.hashCode());
	result = 3*result + (fileData == null ? 0 : fileData.hashCode());
	return result;
    }
}
