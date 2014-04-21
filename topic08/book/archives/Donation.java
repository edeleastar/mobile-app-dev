package app.models;

public class Donation
{
  private int amount;
  private String method;

  public Donation()
  {
  }

  public Donation(int amount, String method)
  {
    this.amount = amount;
    this.method = method;
  }

  public void setAmount(int amount)
  {
    this.amount = amount;
  }

  public void setMethod(String method)
  {
    this.method = method;
  }

  public int getAmount()
  {
    return amount;
  }

  public String getMethod()
  {
    return method;
  }

  public String toString()
  {
    return getClass().getName() + "[" + "amount=" + amount + "," + "method= " + method + "]";
  }
}