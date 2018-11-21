
public class DLink 
{
	
	private Object data;
	private DLink next;
	private DLink prev;
	private DLink otherLink;
	
	
	//constructor for a DLink;
	//input : data , next DLink , previous DLink ;  no output;
	public DLink( Object data , DLink next , DLink prev )
	{
		this.data = data;
		this.next = next;
		this.prev = prev;
		
		if( next != null )
			 next.setPrev( this ); 
		
		if( prev != null )
			 prev.setNext( this );
		
	}
	
	
	//constructor for a DLink with only data , previous and next DLink are null;
	//input : data for the DLink , no output;
	public DLink( Object data )
	{
		 this( data , null , null );
	}
	
	
	public Object getData()
	{
		return data;
	}
	
	
	public DLink getNext()
	{
		return next; 
	}
	
	
	public DLink getPrev()
	{
		return prev;
	}
	
	
	public DLink getOtherLink()
	{
		return otherLink;
	}
	
	
	public void setNext( DLink next )
	{
		this.next = next;
	}
	
	
	public void setPrev( DLink prev )
	{
		this.prev = prev;
	}
	
	
	public void setOtherLink( DLink other )
	{
		this.otherLink = other;
	}
	
	
	public Object setData( Object data )
	{
		Object tmp = this.data;
		this.data = data; 
		return tmp;
	}
	
	
	public String toString()
	{ 
		return data.toString();
	}
	
	
	public boolean equals( Object other ) 
	{  
		if( ! ( other instanceof DLink ) )
			return false;
		
		if( ( ( ( DLink ) other ).getData() ).equals( this.getData() ) )
			return true;
		
		return false;
	}
	


}
