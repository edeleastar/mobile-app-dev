package app.models;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import app.main.DonationAppDB;

public class DbDonationHelper extends SQLiteOpenHelper
{
  static final String DATABASE_NAME    = "donationDatabase";
  static final int    DATABASE_VERSION = 1;
  static final String TABLE_USERS      = "users";
  static final String TABLE_DONATIONS  = "donations";
  // users
  static final String USER_ID          = "user_id"; // primary key
  static final String FIRSTNAME        = "firstname";
  static final String LASTNAME         = "lastname";
  static final String EMAIL            = "email";
  static final String PASSWORD         = "password";
  // donations
  static final String DONATION_ID      = "id";// primary key
  static final String AMOUNT           = "amount";
  static final String METHOD           = "method";
  static final String USER             = "user";
  // the User table
  static final String CREATE_TABLE_USERS = 
      "CREATE TABLE " + TABLE_USERS 
      + "(" + USER_ID + " INTEGER PRIMARY KEY,"
            + FIRSTNAME + " TEXT," 
            + LASTNAME + " TEXT," 
            + EMAIL + " TEXT," 
            + PASSWORD + " TEXT"
      + ")";
  // the Donation table
  static final String CREATE_TABLE_DONATIONS = "CREATE TABLE " + TABLE_DONATIONS + "(" 
      + DONATION_ID + " INTEGER PRIMARY KEY," 
      + AMOUNT + " TEXT," 
      + METHOD + " TEXT)";

  Context context;

  public DbDonationHelper(Context context)
  {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    this.context = context;
    Log.i("CREATE_TABLE_USERS", DbDonationHelper.CREATE_TABLE_USERS);
    Log.i("CREATE_TABLE_DONATIONS", DbDonationHelper.CREATE_TABLE_DONATIONS);
  }
  
  @Override
  public void onCreate(SQLiteDatabase db)
  {
    db.execSQL(CREATE_TABLE_USERS);
    db.execSQL(CREATE_TABLE_DONATIONS);
  }
  
  public void addUser(User user, DonationAppDB app)
  {
    SQLiteDatabase db = this.getWritableDatabase();
    
    ContentValues values = new ContentValues();
    values.put(FIRSTNAME, user.getFirstName());
    values.put(LASTNAME, user.getLastName());
    values.put(EMAIL, user.getEmail());
    values.put(PASSWORD, user.getPassword());

    long id = db.insert(TABLE_USERS, null, values);
    Log.v("Donation", "added user with primary key " + id);
    db.close();
  }

  public boolean validUser(String email, String password) 
  {
  	SQLiteDatabase db = this.getReadableDatabase();
  	String selectQuery = "SELECT  * FROM " + TABLE_USERS + " WHERE EMAIL = ? AND PASSWORD = ?";
  	String[] selectionArgs = new String[] {email,password};
  	Cursor cursor = db.rawQuery(selectQuery,selectionArgs);
  	int count = cursor.getCount();
  	cursor.close();
  	return count != 0;
  }

  public List<User> getAllUsers()
  {
    List<User> users = new ArrayList<User>();
    String selectQuery = "SELECT  * FROM " + TABLE_USERS;

    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);

    if (cursor.moveToFirst())
    {
      int columnIndex = 1;// skip id (==0)
      do
      {
        User user = new User();
        user.setFirstName(cursor.getString(columnIndex++));
        user.setLastName(cursor.getString(columnIndex++));
        user.setEmail(cursor.getString(columnIndex++));
        user.setPassword(cursor.getString(columnIndex));
        columnIndex = 1;

        users.add(user);
      } while (cursor.moveToNext());
    }
    cursor.close();
    return users;
  }

  public void addDonation(Donation donation)
  {
    SQLiteDatabase db = this.getWritableDatabase();

    ContentValues values = new ContentValues();
    values.put(AMOUNT, donation.getAmount());
    values.put(METHOD, donation.getMethod());
    // Insert record
    long id = db.insert(TABLE_DONATIONS, null, values);
    Log.v("Donation", "Table Donation: record id " + Long.toString(id));
    db.close();
  }

  public List<Donation> getDonations()
  {
    List<Donation> donations = new ArrayList<Donation>();
    String selectQuery = "SELECT  * FROM " + TABLE_DONATIONS;

    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);

    if (cursor.moveToFirst())
    {
      int columnIndex = 1;
      do
      {
        Donation donation = new Donation();
        donation.setAmount(Integer.parseInt(cursor.getString(columnIndex++)));
        donation.setMethod(cursor.getString(columnIndex++));
        columnIndex = 1;
        donations.add(donation);
        Log.v("Donation", "Retrieving donations  " + donation);
      } while (cursor.moveToNext());
    }
    cursor.close();
    return donations;
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
  {
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_DONATIONS);
    onCreate(db);
  }
}