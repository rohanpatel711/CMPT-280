/* InvalidArgument280Exception.java
 * ---------------------------------------------
 * Copyright (c) 2004 University of Saskatchewan
 * All Rights Reserved
 * --------------------------------------------- */



/**	This exception is used when a method takes in an invalid argument. */
public class InvalidArgument280Exception extends Exception280
{
	/**	Create an exception with the specified message. <br> 
		Analysis: Time = O(1) */
	public InvalidArgument280Exception(String message)
	{
		super(message);
	}

	/**	Create an exception with the default message. <br> 
		Analysis: Time = O(1) */
	public InvalidArgument280Exception()
	{
		super("InvalidArgument280Exception thrown!");
	}

	/**	A unique class identifier for serializing and deserializing. */
	static final long serialVersionUID = 1l;
}
