select c.conrelid::regclass as table_name,
    string_agg(col.attname, ', ' order by u.attposition) as columns,
    c.conname as constraint_name
from pg_constraint c
         join lateral unnest(c.conkey) with ordinality as u(attnum, attposition) on true
         join pg_class t on (c.conrelid = t.oid)
         join pg_namespace nsp on nsp.oid = t.relnamespace
         join pg_attribute col on (col.attrelid = t.oid and col.attnum = u.attnum)
where contype = 'f'
  and nsp.nspname = 'public'::text
  and not exists(
        select 1
        from pg_index
        where indrelid = c.conrelid
          and (c.conkey::int[] <@ indkey::int[]) /*all columns of foreign key have to present in index*/
          and array_position(indkey::int[], (c.conkey::int[])[1]) = 0 /*ordering of columns in foreign key and in index is the same*/
    )
group by c.conrelid, c.conname, c.oid
order by (c.conrelid::regclass)::text, columns;
