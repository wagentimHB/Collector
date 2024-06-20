package de.wagentim.collector.utils;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public final class XmlUtils
{
	@SuppressWarnings("unchecked")
	public static<T> T readXmlFile(File file, Class<T> obj) throws JAXBException
	{
		if(file == null)
		{
			return null;
		}
		
		JAXBContext context = JAXBContext.newInstance(obj);
		
		return (T)context.createUnmarshaller().unmarshal(file);
	}
	
	public static<T> void toXmlFile(File file, T obj) throws JAXBException
	{
		if(file == null)
		{
			return;
		}
		
		JAXBContext context = JAXBContext.newInstance(obj.getClass());
		Marshaller mar = context.createMarshaller();
		mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		mar.marshal(obj, file);
	}
}
