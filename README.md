# steel-eye
code test

Requirement Assumptions: A user object is received as a JSON document with
any number of arbitrary field A user object may or may not contain all the
necessary fields required to generate a CONCAT code. If the user object
contains all the fields necessary for the code, the code should be generated
and returned. Else a null code may be returned.

Self-defined Assumptions: 1) "firstname" and "surname" in JSON object
correspond to first name and surname of an user. 2) Received JSON document
named "UserObjects.json" is stored in "resource" working directory
