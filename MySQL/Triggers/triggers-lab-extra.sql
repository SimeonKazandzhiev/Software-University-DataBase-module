  
DROP PROCEDURE IF EXISTS createNamesList;
DELIMITER $$
CREATE PROCEDURE createNamesList (
	INOUT namesList varchar(8000)
)
BEGIN
	DECLARE finished INTEGER DEFAULT 0;
	DECLARE empNames varchar(100) DEFAULT "";

	-- declare cursor for employee email
	DEClARE curName
		CURSOR FOR 
			SELECT CONCAT(first_name, ' ', last_name) FROM employees;

	-- declare NOT FOUND handler
	DECLARE CONTINUE HANDLER 
        FOR NOT FOUND SET finished = 1;

	OPEN curName;

	getName: LOOP
		FETCH curName INTO empNames;
		IF finished = 1 THEN 
			LEAVE getName;
		END IF;
		-- build email list
		SET namesList = CONCAT(empNames,';',namesList);
	END LOOP getName;
	CLOSE curName;

END$$
DELIMITER ;

SET @empNames = '';
CALL createNamesList(@empNames);
SELECT @empNames;