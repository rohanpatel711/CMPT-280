/* BilinearIterator280.java
 * ---------------------------------------------
 * Copyright (c) 2004 University of Saskatchewan
 * All Rights Reserved
 * --------------------------------------------- */
 
package lib280.base;

import lib280.exception.BeforeTheStart280Exception;

/**	An Iterator which has routines to move forward and back, 
	and to the first and last items of a collection.  It keeps track of 
	the current item, and also has routines to determine if it is 
	before the start or after the end of the collection. */
public interface BilinearIterator280<I> extends LinearIterator280<I>
{
  
	/**	Go to last item of the data structure. */
	public void goLast();
 
	/**	Move back one item in the data structure. <br>
		@precond !before()
	 	@throws BeforeTheStart280Exception if the cursor is already in the "before" position and cannot be moved back.
	 */
	public void goBack() throws BeforeTheStart280Exception;
 
} 

/*
package lib280.list;


import lib280.base.BilinearIterator280;
import lib280.exception.BeforeTheStart280Exception;
import lib280.exception.ContainerEmpty280Exception;

public class ilinkedIterator280<I> extends LinkedIterator280<I> implements BilinearIterator280<I>
{

	public BilinkedIterator280(BilinkedList280<I> list)
	{
		super(list);
	}

	public BilinkedIterator280(BilinkedList280<I> newList,
			LinkedNode280<I> initialPrev, LinkedNode280<I> initialCur)
	{
		super(newList, initialPrev, initialCur);
	}

	public void  goLast() throws ContainerEmpty280Exception
	{
		if( this.list.isEmpty() ) throw new ContainerEmpty280Exception("Cannot move to last element of an empty list.");

		cur = ((BilinkedList280<I>) list).lastNode();
		if (cur==null)
			prev = null;
		else
			prev = ((BilinkedNode280<I>) cur).previousNode();
	}

	public void goBack() throws BeforeTheStart280Exception
	{
		if (before())
			throw new BeforeTheStart280Exception("Cannot move back since already before().");

		if (after())
			goLast();
		else
		{
			cur = ((BilinkedNode280<I>) cur).previousNode();
			if (cur!=null)
				prev = ((BilinkedNode280<I>) cur).previousNode();
		}
	 }
	public BilinkedIterator280<I> clone()
	{
		return (BilinkedIterator280<I>) super.clone();
	}


}

 */