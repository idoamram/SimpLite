# SimpLite
###SQLite ORM for Android. Simple and easy-to-use.

## Usage

###1. Add a dependency to your build.gradle file
```
compile 'com.simplite:simplite-orm:0.9.2'
```

###2. Create an Entity class
* Add inheritance from the DBObject class
* Add fields
* For each field, create getters and setters
    * The get;set methods must named accordingly to the fields names
        * For exmaple, if you have `firstName` field, you'll have a
        `getFirstName` getter and a `setFirstName` setter
* Add `@Entity` annotation to your class **(MUST)**
```
    @Entity(tableName = "my_cool_entity_table_name")
    public class MyCoolEntity extends DBObject
```
* Add `@PrimaryKey` to your primary key field **(MUST)**
```
    @PrimaryKey(columnName = "my_pk_column_name")
    private String id;
```
* Add  Other columns annotations *(See the full explanation below)*
    * Column - a regular column - ``int age``
    * ForeignKey - when the field is an instance of other entity -
     ``Car myCar``
    * ForeignKeyArray - when the field is an `ArrayList` of instances of other entity -
    ``ArrayList<Car> myCars``

###3. Add meta-data to your manifest
Name | Type | Value | Default value
---- | ---- | ----- | -------------
DATABASE_NAME | String | <DATABASE_FILE_NAME> | default_db
DATABASE_VERSION | Number | <CURRENT_DATABASE_VERSION> | 1
CONFIG_CLASS | String | <FULL_CONIFGURATION_CLASS_NAME> | null
ENTITIES_CLASSES_NAME | String array | <FULL_CLASS_NAMES_ARRAY> | []

#####DATABASE_NAME
Your database file name
```
<meta-data android:name="DATABASE_NAME" android:value="<DATABASE_NAME>" />
```
Exmaple
```
<meta-data android:name="DATABASE_NAME" android:value="my_great_db" />
```

#####DATABASE_VERSION
**Changing this value will cause database upgrade.**
When ***onUpgrade*** method called, all tables will drop (DROP TABLE)
and recreates in ***onCreate***. ALL OF YOUR DATA WILL GET LOST (See CONFIG_CLASS).
```
<meta-data android:name="DATABASE_VERSION" android:value="<DATABASE_VERSION>" />
```
Example
```
<meta-data android:name="DATABASE_VERSION" android:value="6" />
```

#####CONFIG_CLASS
You **MUST specify the *FULL* name, including all packages.**
The configuration class **MUST** implement the ***SimpLiteConfiguration* interface**.
It has *beforeOnCreate*, *afterOnCreate* ,*beforeOnUpgrade*, *afterOnUpgrade*.
For saving your data during upgrade, hold it on *beforeOnUpgrade*,
and insert it on *afterOnCreate*.
```
<meta-data android:name="CONFIG_CLASS" android:value="<FULL_CLASS_NAME>" />
```
Example
```
<meta-data android:name="CONFIG_CLASS" android:value="com.simplite.example.utils.LocalDBConfiguration" />
```

#####ENTITIES_CLASSES_NAME
The **FULL *(include all packages)*** name of each class
that represent an entity (table in the database).
**An Entity *MUST* extend from DBObject class**.
**The entities should separate with a coma**
```
<meta-data android:name="ENTITIES_CLASSES_NAME" android:value="com.simplite.example.entities.EntityOne,
            com.simplite.example.entities.EntityTwo,com.simplite.example.entities.EntityThree" />
```

###Annotations Usage

#####@Entity
######For a class that represents an entity (table) in the database
Method | Expected Value
------ | --------------
tableName | The name of the table that this entity represents

#####@PrimaryKey
######For a field that is a primary key in its table.
Method | Expected Value
------ | --------------
columnName | The name of the column
options | Options to add to the column, like AUTOINCREMENT or UNIQUE

#####@Column
######For any field that is a regular column - not primary or foreign key
Method | Expected Value
------ | --------------
name | The name of the column
options | Options to add to the column, like AUTOINCREMENT or UNIQUE

#####@ForeignKey
######For any field that is an instance of other entity
Method | Expected Value
------ | --------------
valueColumnName | The name of the column in the **current table**
fkColumnName | The name of the column in the **foreign table**
entityClass | The **foreign entity** class
options | Options to add to the column, like AUTOINCREMENT or UNIQUE

#####@ForeignKeyArray
######For any field that is an `ArrayList` of instances of other entity
Method | Expected Value
------ | --------------
valueColumnName | The name of the column in the **current table**
fkColumnName | The name of the column in the **foreign table**
entityClass | The **foreign entity** class
options | Options to add to the column, like AUTOINCREMENT or UNIQUE

### CRUD
```
Person person = new Person(context);
```
#####Create
```
person.create()
```
#####Delete
```
person.delete()
```
#####Update
```
person.save()
```
#####Read
* **findAll**
* **findOne**
* **findAllByColumn**
* **query**
* **rawQuery**
* **count**
* **countByColumn**
* **getAverageByColumn**

#####CRUD in background
Each CRUD method have another method that called [methodName]+"InBackground"(For example: createInBackground), that execute the original method
in an AsyncTask.

###Please feel more than FREE to contact me and give a feedback, i want to improve the library and make it better for you.
###ido.movieditor@gmail.com
