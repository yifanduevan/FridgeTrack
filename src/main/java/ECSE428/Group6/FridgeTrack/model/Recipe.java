package ECSE428.Group6.FridgeTrack.model;

import java.util.*;

// line 9 "model.ump"
// line 82 "model.ump"
public class Recipe
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Recipe Attributes
  private String name;
  private String description;

  private String instruction;
  //Recipe Associations
  private Fridge fridge;
  private List<Item> items;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Recipe(String aName, Fridge aFridge, String aDescription, String aInstruction)
  {
    name = aName;
    description = aDescription;
    instruction = aInstruction;
    boolean didAddFridge = setFridge(aFridge);
    if (!didAddFridge)
    {
      throw new RuntimeException("Unable to create recipe due to fridge. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    items = new ArrayList<Item>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }
  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }
  public boolean setInstruction(String aInstruction)
  {
    boolean wasSet = false;
    instruction = aInstruction;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }
  /* Code from template association_GetOne */
  public Fridge getFridge()
  {
    return fridge;
  }

  public String getDescription() {return description;}
  
  public String getInstruction() {return instruction;}
  /* Code from template association_GetMany */
  public Item getItem(int index)
  {
    Item aItem = items.get(index);
    return aItem;
  }

  public List<Item> getItems()
  {
    List<Item> newItems = Collections.unmodifiableList(items);
    return newItems;
  }

  public int numberOfItems()
  {
    int number = items.size();
    return number;
  }

  public boolean hasItems()
  {
    boolean has = items.size() > 0;
    return has;
  }

  public int indexOfItem(Item aItem)
  {
    int index = items.indexOf(aItem);
    return index;
  }
  /* Code from template association_SetOneToMany */
  public boolean setFridge(Fridge aFridge)
  {
    boolean wasSet = false;
    if (aFridge == null)
    {
      return wasSet;
    }

    Fridge existingFridge = fridge;
    fridge = aFridge;
    if (existingFridge != null && !existingFridge.equals(aFridge))
    {
      existingFridge.removeRecipe(this);
    }
    fridge.addRecipe(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfItems()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Item addItem(String aName, Item.Unit aUnit, ItemCategory aItemCategory, Fridge aFridge)
  {
    return new Item(aName, aUnit, this, aItemCategory, aFridge);
  }

  public boolean addItem(Item aItem)
  {
    boolean wasAdded = false;
    if (items.contains(aItem)) { return false; }
    Recipe existingRecipe = aItem.getRecipe();
    boolean isNewRecipe = existingRecipe != null && !this.equals(existingRecipe);
    if (isNewRecipe)
    {
      aItem.setRecipe(this);
    }
    else
    {
      items.add(aItem);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeItem(Item aItem)
  {
    boolean wasRemoved = false;
    //Unable to remove aItem, as it must always have a recipe
    if (!this.equals(aItem.getRecipe()))
    {
      items.remove(aItem);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addItemAt(Item aItem, int index)
  {  
    boolean wasAdded = false;
    if(addItem(aItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfItems()) { index = numberOfItems() - 1; }
      items.remove(aItem);
      items.add(index, aItem);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveItemAt(Item aItem, int index)
  {
    boolean wasAdded = false;
    if(items.contains(aItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfItems()) { index = numberOfItems() - 1; }
      items.remove(aItem);
      items.add(index, aItem);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addItemAt(aItem, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    Fridge placeholderFridge = fridge;
    this.fridge = null;
    if(placeholderFridge != null)
    {
      placeholderFridge.removeRecipe(this);
    }
    for(int i=items.size(); i > 0; i--)
    {
      Item aItem = items.get(i - 1);
      aItem.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "fridge = "+(getFridge()!=null?Integer.toHexString(System.identityHashCode(getFridge())):"null");
  }
}