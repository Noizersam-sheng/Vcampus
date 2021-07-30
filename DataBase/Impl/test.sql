SELECT *FROM tbllibrary_books WHERE book_name IN ('中国','中');
DELETE FROM tblconsumption WHERE id LIKE '%213193904%';
UPDATE tblconsumption SET id=213191246 WHERE commodity_id="A12" AND commodity_name="康师傅";
SELECT *FROM tblcourse WHERE course_id='BM123456' AND now_num<max_num;