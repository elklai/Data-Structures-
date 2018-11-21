
//Don't change the class name
public class Container
{
	private Point data;//Don't delete or change this field;
	private DLink xDlink;
	private DLink yDlink;
	
	
	//the Container will also point to the xDLink and the yDLink;
	public Container( Point data , DLink xDLink , DLink yDLink )
	{
		this.data = data;
		this.xDlink = xDLink;
		this.yDlink = yDLink;
	}
	
	
	public Point getData()
	{
		return data;
	}
	
	
	public DLink getXDlink()
	{
		return this.xDlink;
	}
	
	
	public DLink getYDlink()
	{
		return this.yDlink;
	}

}
