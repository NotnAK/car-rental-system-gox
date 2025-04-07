FROM quay.io/keycloak/keycloak:24.0.1

# Копируем JSON-файл реалма в стандартную папку импорта
ADD ./realms/GOX-realm.json /opt/keycloak/data/import/realm.json

# Импортируем реалм при сборке — это ускоряет запуск
RUN /opt/keycloak/bin/kc.sh import --dir=/opt/keycloak/data/import/ --override true || exit 0

# Устанавливаем переменные окружения для запуска Keycloak
ENV KEYCLOAK_ADMIN=admin
ENV KEYCLOAK_ADMIN_PASSWORD=admin

EXPOSE 8080

# Запускаем Keycloak в dev-режиме с импортом реалма
ENTRYPOINT ["/opt/keycloak/bin/kc.sh", "-v", "start-dev", "--import-realm"]
