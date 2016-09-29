# springjdbc-jade

## Introduction

平时使用springjdbc时，一直想封装一个公用方法。发现rose-jade封装的很好，留下方便使用

## 修改可以用columns可以定义不规则字段

## 修改可以定义接口后，还能实现自己的自定义实现类 

	interface CustomDAO{
        List<User> findCustom();
	}
	
	@Repository
	class Custom implements CustomDAO{
		@Override
		public List<User> findCustom() {
			// do something
			return null;
		}
		
	}

    @DAO
    interface UserDAO extends CustomDAO{
        // 准备数据 (DDL)
        @SQL("create table user (id int, name varchar(200));")
        void createTable();
        @SQL("insert into user (id, name) values(:1.id, :1.name);")
        int[] insert(List<User> users);
    }
