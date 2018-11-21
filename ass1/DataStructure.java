
public class DataStructure implements DT 
{
	
	//index represents axis 0=axis=x , 1=!axis=y;
	//all data in sortedLists array is SortedLinkedList;
	private SortedLinkedList [] sortedLists;
	
	
	//////////////// DON'T DELETE THIS CONSTRUCTOR ////////////////
	//construct a new DataStructure empty from data;
	public DataStructure()
	{
		Comparator xComp = new XComparator();
		Comparator yComp = new YComparator();
		this.sortedLists = new SortedLinkedList[2];
		this.sortedLists[0] = new SortedLinkedList( xComp );
		this.sortedLists[1] = new SortedLinkedList( yComp );
	}
	
	
	//construct a new DataStructure based of a given SortedLinkedList array;
	//input : SortedLinkedList[] ; output : a new DataStructure based on the input data;
	public DataStructure( SortedLinkedList [] sortedLists )
	{
		this.sortedLists = sortedLists;	
	}
		

	@Override
	//add a new DLink to both X , Y sortedLists in the right place( staying sorted);
	//input : point  ;   no output;
	public void addPoint( Point point ) 
	{
		DLink xDLink = this.sortedLists[0].add( point );
		DLink yDLink = this.sortedLists[1].add( point );
		xDLink.setOtherLink( yDLink );
		yDLink.setOtherLink( xDLink );
		
	}
	
	//make new Point array of the size of counter , starting from a given DLink;
	//input : DLink to start from , counter for how much Points to add;
	//output : Point array from the DLinks data;
	private Point[] SortedLinkedListToArray( DLink current , int counter )
	{
		Point[] output = new Point[ counter ];
		
		for( int i = 0 ;( current != null ) && ( i < counter ) ; i++)
		{
			output[i] = ( Point ) current.getData();
			current = current.getNext();
		}
		
		return output;
	}

	
	@Override
	//make new Point array which all the Points XorY value are bigger than min and smaller than max depends of the axis;
	//input : min parameter , max parameter , axis for the strip;
	//output : Point array of all the points inside the range;
	//if there are no points in range returning null;
	public Point[] getPointsInRangeRegAxis( int min , int max , Boolean axis ) 
	{ 
		DLink current;
		
		if ( axis ) 
			current = sortedLists[0].getFirst();
		else
			current = sortedLists[1].getFirst();
		
		DLink firstToAdd;
		int counter = 0;
		
		while( ( current != null ) && ( getXorY ( ( Point ) current.getData() , axis ) < min ) )
		{
			current = current.getNext();
		}
		
		firstToAdd = current;
		
		while( ( current != null ) && ( getXorY( ( Point ) current.getData() , axis ) <= max ) )
		{
			counter++;
			current = current.getNext();
		}
		
		if( counter == 0)
			return null;
		else
			return SortedLinkedListToArray( firstToAdd , counter );
		
	}

	
	//return the point value of x or y depends on the axis;
	//input : Point , axis  ;  output : the value of x or y;
	private int getXorY( Point p , Boolean axis )
	{
				if ( axis )
					return p.getX();
				else 
					return p.getY();
	}
	
	
	@Override
	//make new Point array which all the Points XorY value are bigger than min and smaller than max depends of the axis;
	//but sorted by the other strip( !axis );
	//input : min parameter , max parameter , axis for the strip;
	//output : Point array of all the points inside the range sorted by the !axis;
	//if there are no points in range returning null;
	public Point[] getPointsInRangeOppAxis(int min, int max, Boolean axis) 
	{	
		DLink current;
		
		if ( axis ) 
		{
			current = sortedLists[1].getFirst();
		}
		else
		{
			current = sortedLists[0].getFirst();
		}
	
		int counter = 0;
		LinkedList listOutput = new LinkedList();
		
		while( current != null )
		{
			if( ( getXorY( ( Point ) current.getData() , axis ) >= min ) &&
				( getXorY( ( Point ) current.getData() , axis ) <= max )    )
			{
				listOutput.add( current.getData() );
				counter++;
			}
				
			current = current.getNext();
		}
			
		if( counter == 0)
		{
			return null;
		}
		else
			return SortedLinkedListToArray( listOutput.getFirst() , counter );
		
	}
	
	
	//make new SortedLinkedList with a given maximum size;
	//input : first DLink to add , Comparator for the SLL sorting ; the size wanted for the new SLL;
	//output : a SLL with the maximum size of 'size';
	private SortedLinkedList getPointsInRangeOppAxis( DLink start , Comparator oppComp , int size )
	{
		SortedLinkedList output = new SortedLinkedList( oppComp );
		DLink curr = start;
		
		while( ( curr != null ) && ( output.getSize() < size ) )
		{
			output.add( curr.getData() );
			curr = curr.getNext();
		}
		return output;
	}
		
	

	@Override
	//returning the density of a given DataStucture;
	//calculating the density by-the number of points divided by the size of the square;
	//no input ; output : the density of the structure;
	//if there are less than 2 points throwing illegal argument;
	public double getDensity() 
	{
		int n = ( this.sortedLists[0] ).getSize();
		if(n<2)
			throw new IllegalArgumentException( "the number of points is illegal for this method" ); 
		
		int xMax = ( ( Point ) ( ( ( this.sortedLists[0] ).getLast() ) ).getData() ).getX();
		int xMin = ( ( Point ) ( ( ( this.sortedLists[0] ).getFirst() ) ).getData() ).getX();
		int yMax = ( ( Point ) ( ( ( this.sortedLists[1] ).getLast() ) ).getData() ).getY();
		int yMin = ( ( Point ) ( ( ( this.sortedLists[1] ).getFirst() ) ).getData() ).getY();
		
		double output = n / ( ( xMax - xMin ) * ( yMax - yMin ) );
		return output;
	}

	
	@Override
	//deleting from the DataStructure all the points that the value of XorY are smaller than min and bigger than max
	//depending of the axis strip;
	//input : min value , max value , axis for strip;
	//output : no output; just deleting the points that are out of range;
	public void narrowRange( int min , int max , Boolean axis ) 
	{
		DLink first , last;
		SortedLinkedList SortedList , oppSortedList;

		if( axis )
		{
			first = sortedLists[0].getFirst();
			last = sortedLists[0].getLast();
			SortedList = sortedLists[0];
			oppSortedList = sortedLists[1];
        }
        else 
        {
        	first = sortedLists[1].getFirst();
			last = sortedLists[1].getLast();
			SortedList = sortedLists[1];
			oppSortedList = sortedLists[0];
        }
		
        while( getXorY( ( Point ) first.getData() , axis ) < min )
		{
			oppSortedList.remove( first.getOtherLink() );
			first = first.getNext();
		}	
        
		while( getXorY( ( Point ) last.getData() , axis ) > max )
		{
			oppSortedList.remove( last.getOtherLink() );
			last = last.getPrev();
		}
		
		first.setPrev( null );
		last.setNext( null );
		SortedList.first = first;
		SortedList.last = last;
	}

	
	@Override
	//returning the bigger axis strip ( X axis or Y axis );
	//no input ; output : boolean axis for the larger strip;
	public Boolean getLargestAxis() 
	{
		int xMax = ( ( Point ) ( ( ( this.sortedLists[0] ).getLast() ) ).getData() ).getX();
		int xMin = ( ( Point ) ( ( ( this.sortedLists[0] ).getFirst() ) ).getData() ).getX();
		int yMax = ( ( Point ) ( ( ( this.sortedLists[1] ).getLast() ) ).getData() ).getY();
		int yMin = ( ( Point ) ( ( ( this.sortedLists[1] ).getFirst() ) ).getData() ).getY();
		return ( xMax - xMin > yMax - yMin );	
	}
	
	
	@Override
	//returning the median point from all points in the DataStructure;
	//input : axis for the chosen strip ; output : Container with the median point depends on the axis strip;
	public Container getMedian( Boolean axis ) 
	{
		Container output;
		int n = this.sortedLists[0].getSize();
		
		if( axis )
		{
			DLink xLink = ( DLink ) this.sortedLists[0].get( n / 2 );
			Point data = ( Point ) xLink.getData();
			DLink yLink = xLink.getOtherLink();
			output = new Container( data , xLink , yLink );
		}
		else
		{
			DLink yLink = ( DLink )this.sortedLists[1].get( n / 2 );
			Point data = ( Point )yLink.getData();
			DLink xLink = yLink.getOtherLink();
			output = new Container( data , xLink , yLink );
		}
		
		return output;
	}

	
	@Override
	//returning the 2 nearest points in the axis strip with a given width;
	//the center of the strip and the width is the container( the container is given from the median function );
	//if there are no 2 points in that strip returning null;
	//input : median container , width of the strip , axis of the strip;
	//output : array of size 2 with the nearest 2 points in the strip;
	public Point[]	nearestPairInStrip( Container container , double width , Boolean axis ) 
	{
		Point[] output = new Point[2];
		DLink curr;
		
		if( axis )
			curr = container.getYDlink();
		else
			curr = container.getXDlink();
		
		LinkedList strip = new LinkedList();
		int mid = getXorY( ( Point ) ( curr.getData() ) , !axis );
		
		while( ( curr != null ) && ( getXorY( ( Point ) ( curr.getData() ) , !axis ) >= ( mid - width ) / 2 ) )
		{
			curr = curr.getPrev();
		}
			
		int counter = 0;
		
		while( ( curr != null ) && ( getXorY( ( Point ) ( curr.getData() ) , !axis ) <= ( mid + width ) / 2 ) )
		{
			strip.add( curr.getData() );
			curr = curr.getNext();
			counter++;
		}
			
		Point[] stripAsArray = SortedLinkedListToArray( strip.getFirst() , counter );
		int l = stripAsArray.length;
		
		if( l < 2 )
			return null;
		
		double minDis = findDistance( stripAsArray[0] , stripAsArray[1] );
		int indexPoint1 = 0;
		int indexPoint2 = 1;
		
		for( int i = 0 ; i < l ; i++ )
		{
			for(int m = i + 1 ; ( m < l ) && ( m < ( i + 7 ) ) ; m++ )
					{
						double dis = findDistance( stripAsArray[i] , stripAsArray[m] );
						if( dis < minDis )
						{
							minDis = dis;
							indexPoint1 = i;
							indexPoint2 = m;
						}
					}	
		}
		
		output[0] = stripAsArray[ indexPoint1 ];
		output[1] = stripAsArray[ indexPoint2 ];
		return output;
	}
				
	
	//returning the distance between 2 given points;
	//input : 2 points  ;  output : the distance between both points;
	private double findDistance( Point a , Point b )
	{
		double aX = a.getX();
		double aY = a.getY();
		double bX = b.getX();
		double bY = b.getY();
		
		return Math.sqrt( ( ( aX - bX ) * ( aX - bX ) ) + ( ( aY - bY ) * ( aY - bY ) ) );
	}
	
	
	@Override
	//returning an array of size 2 with the nearest 2 points from all the DataStructure;
	//returning null if there are no 2 points in the DataStructure;
	//there is no input ;  output : the 2 nearest points;
	public Point[] nearestPair() 
	{
		if( sortedLists[0].getSize() < 2 )
			return null;
		
		if( sortedLists[0].getSize() == 2 )
			return SortedLinkedListToArray( sortedLists[0].first , 2 );
		
		boolean largestAxis = this.getLargestAxis();
		Container mid = this.getMedian( largestAxis );
		int a;
		DLink midLink;
		Comparator thisComparator , otherComparator;
		
		if( largestAxis )
		{
			a=0;
			midLink = mid.getXDlink();
			thisComparator = new XComparator();
			otherComparator = new YComparator();
		}
		else
		{
			a=1;
			midLink = mid.getYDlink();
			thisComparator = new YComparator();
			otherComparator = new XComparator();
		}
		
		int leftSize = ( this.sortedLists[a].getSize() ) / 2;
		int rightSize = ( this.sortedLists[a].getSize() ) - ( leftSize );
		
		SortedLinkedList[] part1 = new SortedLinkedList[2];
		SortedLinkedList[] part2 = new SortedLinkedList[2];
		SortedLinkedList part1Large = new SortedLinkedList( this.sortedLists[a].getFirst() , midLink.getPrev() , thisComparator , leftSize );
		SortedLinkedList part2Large = new SortedLinkedList( midLink , this.sortedLists[a].getLast() , thisComparator , rightSize );
		SortedLinkedList part1Small = this.getPointsInRangeOppAxis( part1Large.getFirst() , otherComparator , leftSize );
		SortedLinkedList part2Small = this.getPointsInRangeOppAxis( part2Large.getFirst() , otherComparator , rightSize );
		
		part1[ a ] = part1Large;
		part1[ 1 - a] = part1Small;
		part2[ a ] = part2Large;
		part2[ 1 - a ] = part2Small;
		
		DataStructure dt1 = new DataStructure( part1 );
		DataStructure dt2 = new DataStructure( part2 );
		Point[] nearestPair1 = dt1.nearestPair();
		Point[] nearestPair2 = dt2.nearestPair();
		double d1 , d2;
		
		if( nearestPair1 == null )
			d1 = 0;
		else
			d1 = this.findDistance( nearestPair1[0] , nearestPair1[1] );
		
		if( nearestPair2 == null )
			d2 = 0;
		else
			d2 = this.findDistance( nearestPair2[0] , nearestPair2[1] );
		
		Point[]nearestPairStrip; 
		
		if( ( ( d1 != 0 ) && ( d1 <= d2 ) ) || ( d2 == 0 ) )
		{
			nearestPairStrip = this.nearestPairInStrip( mid , d1 * 2 , !largestAxis );
			if( ( nearestPairStrip != null ) && ( findDistance( nearestPairStrip[0] , nearestPairStrip[1] ) < d1 ) )
				return nearestPairStrip;
			else
				return nearestPair1;
		}
		else
		{
			nearestPairStrip = this.nearestPairInStrip( mid , d2 * 2 , !largestAxis );
			if( ( nearestPairStrip != null ) && ( findDistance( nearestPairStrip[0] , nearestPairStrip[1] ) < d2 ) )
				return nearestPairStrip;
			else
				return nearestPair2;
		}	
	
	}
	
}



