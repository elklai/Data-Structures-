import java.util.ArrayList;

//SUBMIT
public class BNode implements BNodeInterface {

	// ///////////////////BEGIN DO NOT CHANGE ///////////////////
	// ///////////////////BEGIN DO NOT CHANGE ///////////////////
	// ///////////////////BEGIN DO NOT CHANGE ///////////////////
	private final int t;
	private int numOfBlocks;
	private boolean isLeaf;
	private ArrayList<Block> blocksList;
	private ArrayList<BNode> childrenList;

	/**
	 * Constructor for creating a node with a single child.<br>
	 * Useful for creating a new root.
	 */
	public BNode(int t, BNode firstChild) {
		this(t, false, 0);
		this.childrenList.add(firstChild);
	}

	/**
	 * Constructor for creating a <b>leaf</b> node with a single block.
	 */
	public BNode(int t, Block firstBlock) {
		this(t, true, 1);
		this.blocksList.add(firstBlock);
	}

	public BNode(int t, boolean isLeaf, int numOfBlocks) {
		this.t = t;
		this.isLeaf = isLeaf;
		this.numOfBlocks = numOfBlocks;
		this.blocksList = new ArrayList<Block>();
		this.childrenList = new ArrayList<BNode>();
	}

	// For testing purposes.
	public BNode(int t, int numOfBlocks, boolean isLeaf,
			ArrayList<Block> blocksList, ArrayList<BNode> childrenList) {
		this.t = t;
		this.numOfBlocks = numOfBlocks;
		this.isLeaf = isLeaf;
		this.blocksList = blocksList;
		this.childrenList = childrenList;
	}

	@Override
	public int getT() {
		return t;
	}

	@Override
	public int getNumOfBlocks() {
		return numOfBlocks;
	}

	@Override
	public boolean isLeaf() {
		return isLeaf;
	}

	@Override
	public ArrayList<Block> getBlocksList() {
		return blocksList;
	}

	@Override
	public ArrayList<BNode> getChildrenList() {
		return childrenList;
	}

	@Override
	public boolean isFull() {
		return numOfBlocks == 2 * t - 1;
	}

	@Override
	public boolean isMinSize() {
		return numOfBlocks == t - 1;
	}
	
	@Override
	public boolean isEmpty() {
		return numOfBlocks == 0;
	}
	
	@Override
	public int getBlockKeyAt(int indx) {
		return blocksList.get(indx).getKey();
	}
	
	@Override
	public Block getBlockAt(int indx) {
		return blocksList.get(indx);
	}

	@Override
	public BNode getChildAt(int indx) {
		return childrenList.get(indx);
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((blocksList == null) ? 0 : blocksList.hashCode());
		result = prime * result
				+ ((childrenList == null) ? 0 : childrenList.hashCode());
		result = prime * result + (isLeaf ? 1231 : 1237);
		result = prime * result + numOfBlocks;
		result = prime * result + t;
		return result;
	}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BNode other = (BNode) obj;
		if (blocksList == null) {
			if (other.blocksList != null)
				return false;
		} else if (!blocksList.equals(other.blocksList))
			return false;
		if (childrenList == null) {
			if (other.childrenList != null)
				return false;
		} else if (!childrenList.equals(other.childrenList))
			return false;
		if (isLeaf != other.isLeaf)
			return false;
		if (numOfBlocks != other.numOfBlocks)
			return false;
		if (t != other.t)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "BNode [t=" + t + ", numOfBlocks=" + numOfBlocks + ", isLeaf="
				+ isLeaf + ", blocksList=" + blocksList + ", childrenList="
				+ childrenList + "]";
	}

	// ///////////////////DO NOT CHANGE END///////////////////
	// ///////////////////DO NOT CHANGE END///////////////////
	// ///////////////////DO NOT CHANGE END///////////////////
	
	
	
	@Override
	public Block search(int key) 
	{
		int i = 0;
		
		while( ( i < numOfBlocks ) && ( key > this.getBlockKeyAt( i ) ) )
			i++;
		
		if( ( i < numOfBlocks ) && ( key == this.getBlockKeyAt( i ) ) )
			return this.getBlockAt( i );
		
		if( isLeaf )
			return null;
		
		return ( this.getChildAt( i ) ).search( key );
	}

	
	public void splitChild( int childIndex )
	{	
		BNode y = this.getChildAt( childIndex );
		BNode z = new BNode( this.getT() , y.isLeaf() , 0 );
		
		for( int j = 0 ; j < this.getT() - 1 ; j++ )
			z.getBlocksList().add( y.getBlocksList().remove( this.getT() ) );
		
		if( ! y.isLeaf() )
		{
			for( int j = 0 ; j < this.getT() ; j++ )
				z.getChildrenList().add( y.getChildrenList().remove( this.getT() ) );	
		}
		
		this.getChildrenList().add( childIndex + 1 , z );
		this.getBlocksList().add( childIndex , y.getBlocksList().remove( this.getT() - 1 ) );
		this.numOfBlocks++;
		y.numOfBlocks = this.getT() - 1;
		z.numOfBlocks = this.getT() - 1;
	
	}
	
	
	@Override
	public void insertNonFull(Block d) 
	{
		int i = this.getNumOfBlocks();
		
		if( this.isLeaf )
		{
			this.getBlocksList().add( d );
			while ( ( i > 0 ) && ( d.getKey() < this.getBlockKeyAt( i - 1 ) ) )
			{
				this.blocksList.set( i , this.getBlockAt( i - 1 ) );
				i = i - 1;
			}
			
			this.blocksList.set( i , d );
			this.numOfBlocks++ ;
		}
		else
		{
			while ( ( i > 0 ) && ( d.getKey() < this.getBlockKeyAt( i - 1 ) ) )
				i = i - 1;
			
			if( this.getChildAt( i ).isFull() )
			{
				this.splitChild( i );
				if( d.getKey() > this.getBlockKeyAt( i ) )
					i = i + 1;
			}
			
			this.getChildAt( i ).insertNonFull( d );
		}
	}

	
	/**
	* True iff the child node at childIndx-1 exists and has more than t-1 blocks.
	* @param childIndx  
	* @return  
	*/  
	private boolean childHasNonMinimalLeftSibling( int childIndx )
	{
		if( ( childIndx < 0 ) || ( childIndx >= this.getChildrenList().size() ) )
			throw new IllegalArgumentException( "index is illigal" );
		
		if( childIndx == 0 )
			return false;
		
		BNode leftSibling = this.getChildAt( childIndx - 1 );
		return ( ( leftSibling != null ) && ( ! leftSibling.isMinSize() ) ); 
	}
	
	
	/**
	* True iff the child node at childIndx+1 exists and has more than t-1 blocks.
	* @param childIndx
	* 
	* @return
	*/
	private boolean childHasNonMinimalRightSibling( int childIndx )
	{
		if( ( childIndx < 0 ) || ( childIndx >= this.getChildrenList().size() ) )
			throw new IllegalArgumentException( "index is illigal" );
		
		if( childIndx == this.getChildrenList().size() - 1 )
			return false;
		
		BNode rightSibling = this.getChildAt( childIndx + 1 );
		return ( ( rightSibling != null ) && ( ! rightSibling.isMinSize() ) ); 
	}
	
	
	/**
	* Verifies the child node at childIndx has at least t blocks.<br>
	* If necessary a shift or merge is performed.
	*
	* @param childIndxxs
	*/
	private void shiftOrMergeChildIfNeeded(int childIndx)
	{
		if( this.getChildAt( childIndx ).isMinSize() )
		{
			if( this.childHasNonMinimalLeftSibling( childIndx ) )
				this.shiftFromLeftSibling( childIndx );
			else 
				if( this.childHasNonMinimalRightSibling( childIndx ) )
					this.shiftFromRightSibling( childIndx );
				else
					this.mergeChildWithSibling( childIndx );
		}
	}
	
	
	/**
	* Merges the child node at childIndx with its left or right sibling.
	* @param childIndx
	*/
	private void mergeChildWithSibling( int childIndx )
	{
		if ( childIndx > 0 )
			this.mergeWithLeftSibling( childIndx );
		else
		{
			if ( childIndx < this.getChildrenList().size() - 1 )
				this.mergeWithRightSibling( childIndx );
			else
				throw new IllegalArgumentException( "no sibling" );
		}
	}
	
	
	/**
	* Merges the child node at childIndx with its left sibling.<br>
	* The left sibling node is removed.
	* @param childIndx
	*/
	private void mergeWithLeftSibling( int childIndx )
	{
		BNode leftSibling , child ;
		leftSibling = this.getChildAt( childIndx -1 );
		child = this.getChildAt( childIndx );
		
		child.getBlocksList().add( 0 , this.getBlocksList().remove( childIndx - 1 ) );
		
		for( int i = 0 ; i < leftSibling.numOfBlocks ; i++ )
			child.getBlocksList().add( i , leftSibling.getBlocksList().remove( 0 ) );
		
		child.numOfBlocks = child.numOfBlocks + 1 + leftSibling.numOfBlocks;
		
		for( int i = 0 ; i < leftSibling.getChildrenList().size() ; i++ )
			child.getChildrenList().add( i , leftSibling.getChildrenList().remove( 0 ) );
		
		this.getChildrenList().remove( childIndx - 1 );
		this.numOfBlocks--;
	}
	
	
	/**
	* Merges the child node at childIndx with its right sibling.<br>
	* The right sibling node is removed.
	* @param childIndx
	*/
	private void mergeWithRightSibling( int childIndx )
	{
		BNode rightSibling , child ;
		rightSibling = this.getChildAt( childIndx + 1 );
		child = this.getChildAt( childIndx );
		
		child.getBlocksList().add( this.getBlocksList().remove( childIndx ) );
		
		for( int i = 0 ; i < rightSibling.getNumOfBlocks() ; i++ )
			child.getBlocksList().add( rightSibling.getBlocksList().remove( 0 ) );
		
		child.numOfBlocks = child.getNumOfBlocks() + 1 + rightSibling.getNumOfBlocks();
		
		for( int i = 0 ; i < rightSibling.getChildrenList().size() ; i++ )
			child.getChildrenList().add( rightSibling.getChildrenList().remove( 0 ) );
		
		this.getChildrenList().remove( childIndx + 1 );
		this.numOfBlocks--;
	}
	
	
	/**
	* Add additional block to the child node at childIndx, by shifting from left sibling.
	* @param childIndx
	*/
	private void shiftFromLeftSibling( int childIndx ) 
	{
		Block d1 , d2;
		BNode leftSibling , child , childToMove;
		
		leftSibling = this.getChildAt( childIndx - 1 );
		child = this.getChildAt( childIndx );
		
		if( ! child.isLeaf )
		{
			childToMove = leftSibling.getChildrenList().remove( leftSibling.getChildrenList().size() -1 );
			child.getChildrenList().add( 0 , childToMove );
		}
		
		d1 = this.blocksList.remove( childIndx - 1 );
		d2 = leftSibling.blocksList.remove( leftSibling.getNumOfBlocks() - 1 );
		
		child.getBlocksList().add( 0 , d1 );
		this.getBlocksList().add( childIndx - 1 , d2 );
		
		leftSibling.numOfBlocks -- ;
		child.numOfBlocks ++ ;
	}

	
	/**
	* Add additional block to the child node at childIndx, by shifting from right sibling.
	* @param childIndx
	*/
	private void shiftFromRightSibling( int childIndx ) 
	{
		Block d1 , d2;
		BNode rightSibling , child , childToMove;
		
		rightSibling = this.getChildAt( childIndx + 1 );
		child = this.getChildAt( childIndx );
		
		if( ! child.isLeaf )
		{
			childToMove = rightSibling.getChildrenList().remove( 0 );
			child.getChildrenList().add( childToMove );
		}
		
		d1 = this.blocksList.remove( childIndx );
		d2 = rightSibling.blocksList.remove( 0 );
		
		child.getBlocksList().add( d1 );
		this.getBlocksList().add( childIndx , d2 );
		
		rightSibling.numOfBlocks -- ;
		child.numOfBlocks ++ ;
	}
	
	
	@Override
	public void delete( int key ) 
	{
		boolean found = false;
		int i = 0;
		Block toReplace;
		
		while( i < this.numOfBlocks && !found )
		{
			if( key > this.getBlockKeyAt( i ) )
				i++;
			else
				found = true;
		}
		
		int check = this.getNumOfBlocks();
		if( ( ( i == this.getNumOfBlocks() ) ) || ( key != getBlockKeyAt( i ) ) )
		{
			if( this.isLeaf )
				return;
			
			this.shiftOrMergeChildIfNeeded( i );
			
			if( ( check != this.getNumOfBlocks() ) && ( i != 0 ) )
				i--;
			
			this.getChildAt( i ).delete( key );
		}
		else
		{
			if( this.isLeaf )  // case 1;
			{
				this.blocksList.remove( i );
				this.numOfBlocks--;
			}
			else
			{
				if( ! this.getChildAt( i ).isMinSize() )  // case 2;
				{
					toReplace = this.getChildAt( i ).getMaxKeyBlock();
					this.getChildAt( i ).delete( toReplace.getKey() );
					this.blocksList.set( i , toReplace );
				}
				else
				{
					if( ! this.getChildAt( i + 1 ).isMinSize() )  // case 3;
					{
						toReplace = this.getChildAt( i +1 ).getMinKeyBlock();
						this.getChildAt( i + 1 ).delete( toReplace.getKey() );
						this.blocksList.set( i , toReplace );
					}
					else  // case 4;
					{
						this.mergeWithRightSibling( i );
						this.delete( key );
					}
				}		
			}
			
		}
		
	}

	
	/**
	* Finds and returns the block with the min key in the subtree.
	* @return min key block
	*/
	private Block getMinKeyBlock() 
	{
		if( this.isLeaf )
		{
			return this.getBlockAt( 0 );			
		}
		else
		{
			if( this.getChildAt( 0 ).isLeaf )
				return this.getBlockAt( 0 );
			else
				return this.getChildAt( 0 ).getMinKeyBlock();
		}	
	}
	
	
	
	/**
	* Finds and returns the block with the max key in the subtree.
	* @return max key block
	*/
	private Block getMaxKeyBlock()
	{
		if( this.isLeaf )
			return this.getBlockAt( this.numOfBlocks - 1 );
		
		else
		{
			if( this.getChildAt( this.getChildrenList().size() - 1 ).isLeaf )
				return this.getBlockAt( this.numOfBlocks - 1 );
			else
				return this.getChildAt( this.getChildrenList().size() - 1 ).getMaxKeyBlock();
		}
	}
	
	
	@Override
	public MerkleBNode createHashNode() 
	{
		MerkleBNode output;
		ArrayList< byte[] > dataList = new ArrayList< byte[] >();
		
		if( this.isLeaf() )
		{
			if( ! this.isEmpty() )
				for( int i = 0 ; i < this.getNumOfBlocks() ; i++ )
					dataList.add( this.getBlockAt( i ).getData() );
			
			output = new MerkleBNode( HashUtils.sha1Hash( dataList ) );
		}
		else
		{
			ArrayList< MerkleBNode > childrenMNode = new ArrayList< MerkleBNode >();
			
			for( int i = 0 ; i < this.getChildrenList().size() ; i++ )
				childrenMNode.add( this.getChildAt( i ).createHashNode() );		
			
			for( int j = 0 ; j < this.getNumOfBlocks() ; j++ )
			{
				dataList.add( childrenMNode.get( j ).getHashValue() );
				dataList.add( this.getBlockAt( j ).getData() );
			}
			dataList.add( childrenMNode.get( this.getNumOfBlocks() ).getHashValue() );
			
			
			output = new MerkleBNode( HashUtils.sha1Hash( dataList ) , childrenMNode );
		}
		
		return output;
	}
	
}

