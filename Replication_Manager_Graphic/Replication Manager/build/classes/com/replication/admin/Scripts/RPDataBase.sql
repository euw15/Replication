
CREATE TABLE connections( 
    idConnection	INT IDENTITY(1,1) PRIMARY KEY NOT NULL, 
	DBMSInput		VARCHAR(45) NOT NULL,
	ipInput			VARCHAR(45) NOT NULL,
	DBNameInput		VARCHAR(45) NOT NULL,
	userInput		VARCHAR(45) NOT NULL,
	passwordInput	VARCHAR(45) NOT NULL,
	DBMSOutput		VARCHAR(45) NOT NULL, 
	ipOutput		VARCHAR(45) NOT NULL,
	DBNameOutput	VARCHAR(45) NOT NULL,
	userOutput		VARCHAR(45) NOT NULL,
	passwordOutput	VARCHAR(45) NOT NULL,
	stateConnection	INT NOT NULL);

-------------------------------------------------------------

 CREATE PROCEDURE InsertConnection
	@DBMSInput		VARCHAR(45),
	@ipInput		VARCHAR(45),
	@DBNameInput	VARCHAR(45),
	@userInput		VARCHAR(45),
	@passwordInput	VARCHAR(45),
	@DBMSOutput		VARCHAR(45), 
	@ipOutput		VARCHAR(45),
	@DBNameOutput	VARCHAR(45),
	@userOutput		VARCHAR(45),
	@passwordOutput	VARCHAR(45)
AS 
BEGIN 
     SET NOCOUNT ON 
     INSERT  INTO dbo.connections
	  ( 
            DBMSInput,
            ipInput,
            DBNameInput,
            userInput,
            passwordInput,
            DBMSOutput,
			ipOutput,
            DBNameOutput,
			userOutput,
			passwordOutput
      ) 
     VALUES 
         ( 
            @DBMSInput,
	        @ipInput,
	        @DBNameInput,
	        @userInput,
	        @passwordInput,
	        @DBMSOutput, 
	        @ipOutput,
	        @DBNameOutput,
	        @userOutput,
	        @passwordOutput	               
          );

END 


--------------------------------------------------------------------------
 CREATE PROCEDURE setActiveOrPause
	(@idConnection	INT)	
AS 
BEGIN 
     SET NOCOUNT ON 
     UPDATE dbo.connections
	 SET stateConnection = 1 - stateConnection
	 WHERE  idConnection = @idConnection;  

END 


----------------------------------------------------------------------------
CREATE TABLE history( 
    idHistory	    INT IDENTITY(1,1) PRIMARY KEY NOT NULL, 
	typeEvent		INT NOT NULL,
	[description]   VARCHAR(200) NOT NULL,
	[date]          DATETIME NOT NULL
	);
----------------------------------------------------------------------------


 CREATE PROCEDURE InsertHistoryEvent
	@typeEvent		INT,
	@description  	VARCHAR(200)
AS 
BEGIN 
     SET NOCOUNT ON 
     INSERT  INTO dbo.history
	  ( 
            typeEvent,
            [description],
			[date]
           
      ) 
     VALUES 
         ( 
		    @typeEvent,
            @description	
			,GETDATE()               
          );

END 
-----------------------------------------------------------------------
CREATE Trigger AfterInsertConnection ON dbo.connections
AFTER INSERT
AS
BEGIN 
     SET NOCOUNT ON 
     INSERT  INTO dbo.history
	  ( 
            typeEvent,
            [description],
			[date]

           
      ) 
     VALUES 
         ( 
		    1001,
            'Se creo una nueva coneccion entre '+ (SELECT DBNameInput FROM inserted)+' que esta en, '+
			(SELECT DBMSInput FROM inserted) +' a '	+ (SELECT DBNameOutput FROM inserted)+' que esta en, '+
			(SELECT DBMSOutput FROM inserted),
			GETDATE()           
          );

END  
------------------------------------------------------------------------------------------------------
CREATE Trigger AfterUpdateConnection ON dbo.connections
AFTER UPDATE
AS
BEGIN 
	DECLARE @event VARCHAR(100)='';
	IF (SELECT stateConnection FROM inserted) = 1 BEGIN
		SET @event = 'Se activo la connecion numero:'+str((SELECT idConnection FROM inserted)) +' entre:';
		 
	END
	ELSE BEGIN
		SET @event = 'Se desactivo la connecion numero:'+str((SELECT idConnection FROM inserted)) +' entre:';
	END
		
   
     SET NOCOUNT ON 
     INSERT  INTO dbo.history
	  ( 
            typeEvent,
            [description],
			[date]

           
      ) 
     VALUES 
         ( 
		    1002,
            @event +' '+ (SELECT DBNameInput FROM inserted)+' que esta en, '+
			(SELECT DBMSInput FROM inserted) +' a '	+ (SELECT DBNameOutput FROM inserted)+' que esta en, '+
			(SELECT DBMSOutput FROM inserted),
			GETDATE()           
          );

END  

CREATE TABLE [Log](
	[idLog] [int] IDENTITY(1,1) NOT NULL,
	[table_name] [nchar](200) NULL,
	[action] [nchar](200) NULL,
	[row_pk] [nchar](200) NULL,
	[field_name] [nchar](200) NULL,
	[old_value] [nchar](200) NULL,
	[new_value] [nchar](200) NULL,
	[timestamp] [datetime] NULL DEFAULT (getdate()),
	[consultado] [bit] NULL DEFAULT ((0)),
	[nombreBaseOrigen] [nchar] (200) NULL
) ON [PRIMARY]