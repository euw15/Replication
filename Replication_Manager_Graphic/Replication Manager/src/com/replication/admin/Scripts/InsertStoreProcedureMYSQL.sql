CREATE PROCEDURE  addLogTrigger(IN tableName VARCHAR(255), IN pkField VARCHAR(255))

BEGIN
  DECLARE done BOOLEAN DEFAULT 0;
  DECLARE _output TEXT DEFAULT '';
  DECLARE _partial TEXT DEFAULT '';
  DECLARE curUpdate CURSOR FOR      
     SELECT CONCAT(
          'IF NOT( OLD.', column_name, ' <=> NEW.', column_name, ') THEN INSERT INTO Historial (',
                    'table_name, ',
                    'action, ',
                    'row_pk, ',
                    'field_name, ',
                    'old_value, ',
                    'new_value,',
                    'timestamp'
                    ') VALUES ( ',
                    '''', table_name, ''', ', 
                    '''UPDATE'', NEW.',
                    pkField,
                    ',''', column_name, ''', ',
                    'OLD.', column_name, ', ',
                    'NEW.', column_name, ', ',
                    'timestamp',
                '); END IF;\n'
            ) 
        FROM 
            information_schema.columns 
        WHERE 
            table_schema = database()
            AND table_name = tableName;

  DECLARE curInsert CURSOR FOR      
     SELECT CONCAT(
          'INSERT INTO Historial (',
                    'table_name, ',
                    'action, ',
                    'row_pk, ',
                    'field_name, ',
                    'old_value, ',
                    'timestamp'
                    ') VALUES ( ',
                    '''', table_name, ''', ', 
                    '''INSERT'', NEW.',
                    pkField,
                    ',''', column_name, ''', ',
                    'NEW.', column_name, ', ',
                    'timestamp',
                '); \n'
            ) 
        FROM 
            information_schema.columns 
        WHERE 
            table_schema = database()
            AND table_name = tableName;

  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done=1;



    -- ------------------------------------ THE INSERT TRIGGER ------------------------------------
    SELECT  concat('DELIMITER $\n', 'CREATE TRIGGER ', tableName, '_INSERT_AU AFTER INSERT ON ', tableName, ' FOR EACH ROW BEGIN \nDECLARE timestamp DATE DEFAULT now();\n') into _output;
    SET done = 0;
    OPEN curInsert;
    
    REPEAT
      FETCH curInsert into _partial;
      IF NOT done THEN
        SET _output = CONCAT(_output, _partial, '\n');
      END IF;
    UNTIL done END REPEAT;
    
    CLOSE curInsert;

    SET _output = CONCAT(_output,'\n', ' END;$\n' );



    -- ------------------------------------ THE UPDATE TRIGGER ------------------------------------
    SELECT  concat('DELIMITER $\n', 'CREATE TRIGGER ', tableName, '_UPDATE_AU AFTER UPDATE ON ', tableName, ' FOR EACH ROW BEGIN \nDECLARE timestamp DATE DEFAULT now();\n') into _partial;
    SET done = 0;
    SET _output = CONCAT(_output, _partial, '\n' );
    
    OPEN curUpdate;
    
    REPEAT
      FETCH curUpdate into _partial;
      IF NOT done THEN
        SET _output = CONCAT(_output, _partial, '\n');
      END IF;
    UNTIL done END REPEAT;
    
    CLOSE curUpdate;

    SET _output = CONCAT(_output,'\n', ' END;$\n' );


    -- ------------------------------------ THE DELETE TRIGGER ------------------------------------


    SELECT  concat('CREATE TRIGGER ', tableName, '_DELETE_AU AFTER DELETE ON ', tableName, ' FOR EACH ROW BEGIN \nDECLARE timestamp DATE DEFAULT now();\n') into _partial;
    SET _output = CONCAT(_output, _partial, '\n' );
    SELECT concat('INSERT INTO Historial (',
                    'table_name, ',
                    'action, ',
                    'row_pk,',
                    'timestamp',
            ') VALUES ( ',
                    '''', tableName, ''', ', 
                    '''DELETE'', OLD.',
                    pkField, 
                    ', timestamp',
                ');\n') INTO _partial;
    SET _output = CONCAT(_output, _partial, '\n', ' END;$\n' );


    -- ALL DONE -- OUTPUT
    SELECT _output;


END