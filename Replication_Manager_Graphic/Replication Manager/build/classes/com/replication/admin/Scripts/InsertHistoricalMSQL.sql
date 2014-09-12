IF NOT EXISTS (SELECT * FROM sys.tables t INNER JOIN sys.schemas s on t.schema_id = s.schema_id WHERE s.name = 'dbo' and t.name = 'Historial' )
CREATE TABLE [dbo].[Historial](
	[idHistorial] [int] IDENTITY(1,1) NOT NULL,
	[table_name] [nchar](200) NULL,
	[action] [nchar](200) NULL,
	[row_pk] [nchar](200) NULL,
	[field_name] [nchar](200) NULL,
	[old_value] [nchar](200) NULL,
	[new_value] [nchar](200) NULL,
	[timestamp] [datetime] NULL DEFAULT (getdate()),
	[consultado] [bit] NULL DEFAULT ((0))
);
