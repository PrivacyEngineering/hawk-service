-- gets the field id, field name, legal base reference and legal base description
SELECT id, name, js.reference, js.description
FROM (
SELECT field.id, field.name, js.value as legal_bases
FROM field, json_array_elements(field.legal_bases::json) as js
) AS lb, json_to_record(lb.legal_bases) as js(reference text, description text)
;