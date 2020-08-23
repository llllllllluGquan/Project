package com.example.lgq.util;


public class Info {
//	private static  String IP="192.168.1.118";
//	private static  String IP="192.168.1.176";
	private static  String IP="192.168.204.25";
	public static final String path1="http://"+IP+":8080/Liibrary/Login";//登录验证
	public static final String path2="http://"+IP+":8080/Liibrary/AllSeatInfo";//查看所有座位
	public static final String path3="http://"+IP+":8080/Liibrary/IsBookSeat";//核对该账户是否可以进行订座
	public static final String path4="http://"+IP+":8080/Liibrary/UpdateUserBookseatState";//用户订座进行修改user表中的can_book_seat属性
	public static final String path5="http://"+IP+":8080/Liibrary/BookSeatUpdateuser";//修改user_seat表的info
	public static final String path6="http://"+IP+":8080/Liibrary/BookSeat";//修改seat表的state
	public static final String path7="http://"+IP+":8080/Liibrary/SeatInfoByCondition?position=";//按照位置查找座位
	public static final String path8="http://"+IP+":8080/Liibrary/GetUserinfo?username=";//得到个人信息
	public static final String path9="http://"+IP+":8080/Liibrary/Updateanthor_name?username=";//修改昵称
	public static final String path10="http://"+IP+":8080/Liibrary/UpdateAddress?username=";//修改家庭住址
	public static final String path11="http://"+IP+":8080/Liibrary/UpdateSignature?username=";//修改个性签名
	public static final String path12="http://"+IP+":8080/Liibrary/UpdateSex?username=";//修改性别
	public static final String path13="http://"+IP+":8080/Liibrary/AllBookInfo";//查找所有书
	public static final String path14="http://"+IP+":8080/Liibrary/IsCanborrow?username=";//是否可以借书
	public static final String path15="http://"+IP+":8080/Liibrary/UpdateBorrowbookCount?username=";//修改借书的数量
	public static final String path16="http://"+IP+":8080/Liibrary/UpdateBookMargin?booknum=";//修改图书的数量
	public static final String path17="http://"+IP+":8080/Liibrary/UpdateUser_bookInfo?type=";//更新user_book表的信息
	public static final String path18="http://"+IP+":8080/Liibrary/QuerySeatInfo?username=";//查看占座的信息
	public static final String path19="http://"+IP+":8080/Liibrary/QuerybookInfo?username=";//查看借阅的信息
	public static final String path20="http://"+IP+":8080/Liibrary/UnsubscribeSeat?username=";//退订座位
	public static final String path21="http://"+IP+":8080/Liibrary/ReturnBook?username=";//归还图书修改user_book 里面的info
	public static final String path23="http://"+IP+":8080/Liibrary/UpdatecanBookWhilereturn?username=";//归还图书 修改user里的 can_borrow_book
	public static final String path24="http://"+IP+":8080/Liibrary/UpdatebookMarginWhilereturn?booknum=";//归还图书 修改book 里的 margin
	public static final String path22="http://"+IP+":8080/Liibrary/ExtraBook?username=";//续借图书
}
