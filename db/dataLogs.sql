select * from logging_event;
select * from logging_event_exception;
select * from logging_event_property;

delete from  logging_event_property;
delete from  logging_event_exception;
delete from  logging_event;

select p.mapped_key, p.mapped_value, e.*
from logging_event e
  left join logging_event_property p on e.event_id = p.event_id