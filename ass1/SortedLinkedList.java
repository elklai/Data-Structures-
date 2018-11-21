public class SortedLinkedList extends LinkedList 
{
	
	private Comparator myComparator;


	public SortedLinkedList( Comparator comp ) 
	{
	   super();
	   
	   this.myComparator = comp;
    }
   
	
	public SortedLinkedList( DLink first , DLink last , Comparator comp , int size )
	{
	   this.first = first;
	   this.last = last;
	   this.size = size;
	   this.myComparator = comp;
	   
    }

    //add a given element in a sorted way to the LinkedList;
	public DLink add( Object element ) 
	{
	   boolean found = false;
	   DLink curr = this.getFirst();
	   
	   if( ! ( element instanceof Point ) )
	   	   throw new ClassCastException() ;
	   
	   if( this.getFirst() == null )
		   return super.add( element );
	   else
	   {
		   while( ! found & curr != null )
		   {
			   if( myComparator.compare( element , curr.getData() ) > 0 )
					found = true ;
			   else
					curr = curr.getNext();
		   }

		   if ( curr == null )
		   {
		  		// Add the new element at the end of the list
		   		return super.add( element ); 
		   }
		   else
		   {
		   		DLink toAdd = new DLink( element , curr , curr.getPrev() );
		   		
		   		if( toAdd.getPrev() == null )
		   			this.first = toAdd;
		   		
		   		this.size++;
		   		return toAdd;
		   	}
	   }	   
   }
   
	
   public Object set( int index , Object element )
   {
	   //Works just like super.set(index, element) 
       //if element is comparable and the operation 
       //preserves the invariant (the list is always
       // sorted).
	   return null;
   }
 

   public void addFirst( Object element ) 
   {
	   //Works just like super.add(element) 
       //if element is comparable and the operation 
       //preserves the invariant (the list is always
       // sorted).
   }

   
}
