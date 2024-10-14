CREATE TABLE IF NOT EXISTS role (
                                    id VARCHAR(50) PRIMARY KEY,
                                    name VARCHAR(100) NOT NULL
);

INSERT INTO public.role (id, name) VALUES ('DOCT', 'Doctor')
ON CONFLICT (id) DO UPDATE SET name = excluded.name;

INSERT INTO public.role (id, name) VALUES ('PTNT', 'Paciente')
ON CONFLICT (id) DO UPDATE SET name = excluded.name;

INSERT INTO public.role (id, name) VALUES ('ASST', 'Asistente')
ON CONFLICT (id) DO UPDATE SET name = excluded.name;

INSERT INTO public.role (id, name) VALUES ('ADMI', 'Administrador')
ON CONFLICT (id) DO UPDATE SET name = excluded.name;

CREATE TABLE IF NOT EXISTS public.specialties (
                                                  id VARCHAR(50) PRIMARY KEY,
                                                  name VARCHAR(100) NOT NULL
);

INSERT INTO public.specialties (id, name) VALUES ('CARD', 'Cardiología')
ON CONFLICT (id) DO UPDATE SET name = excluded.name;

INSERT INTO public.specialties (id, name) VALUES ('NEUR', 'Neurología')
ON CONFLICT (id) DO UPDATE SET name = excluded.name;

INSERT INTO public.specialties (id, name) VALUES ('ONCO', 'Oncología')
ON CONFLICT (id) DO UPDATE SET name = excluded.name;

INSERT INTO public.specialties (id, name) VALUES ('PEDI', 'Pediatría')
ON CONFLICT (id) DO UPDATE SET name = excluded.name;

INSERT INTO public.specialties (id, name) VALUES ('DERM', 'Dermatología')
ON CONFLICT (id) DO UPDATE SET name = excluded.name;

INSERT INTO public.specialties (id, name) VALUES ('ENDO', 'Endocrinología')
ON CONFLICT (id) DO UPDATE SET name = excluded.name;

INSERT INTO public.specialties (id, name) VALUES ('GAST', 'Gastroenterología')
ON CONFLICT (id) DO UPDATE SET name = excluded.name;

INSERT INTO public.specialties (id, name) VALUES ('GINE', 'Ginecología')
ON CONFLICT (id) DO UPDATE SET name = excluded.name;

INSERT INTO public.specialties (id, name) VALUES ('HEMA', 'Hematología')
ON CONFLICT (id) DO UPDATE SET name = excluded.name;

INSERT INTO public.specialties (id, name) VALUES ('INMU', 'Inmunología')
ON CONFLICT (id) DO UPDATE SET name = excluded.name;

INSERT INTO public.specialties (id, name) VALUES ('NEFR', 'Nefrología')
ON CONFLICT (id) DO UPDATE SET name = excluded.name;

INSERT INTO public.specialties (id, name) VALUES ('NEUM', 'Neumología')
ON CONFLICT (id) DO UPDATE SET name = excluded.name;

INSERT INTO public.specialties (id, name) VALUES ('OFAL', 'Oftalmología')
ON CONFLICT (id) DO UPDATE SET name = excluded.name;

INSERT INTO public.specialties (id, name) VALUES ('ORTO', 'Ortopedia')
ON CONFLICT (id) DO UPDATE SET name = excluded.name;

INSERT INTO public.specialties (id, name) VALUES ('OTOR', 'Otorrinolaringología')
ON CONFLICT (id) DO UPDATE SET name = excluded.name;

INSERT INTO public.specialties (id, name) VALUES ('PSIQ', 'Psiquiatría')
ON CONFLICT (id) DO UPDATE SET name = excluded.name;

INSERT INTO public.specialties (id, name) VALUES ('REUM', 'Reumatología')
ON CONFLICT (id) DO UPDATE SET name = excluded.name;

INSERT INTO public.specialties (id, name) VALUES ('UROL', 'Urología')
ON CONFLICT (id) DO UPDATE SET name = excluded.name;
