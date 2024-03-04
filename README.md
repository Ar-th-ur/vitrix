# Vitrtix

Этот проект представляет собой пародию на [Pinterest](https://ru.pinterest.com/).
В нём также можно размещать свои посты, смотреть и искать другие.
Присутствует панель администратора, позволяющая мониторить других пользователей,
и в случае нарушения каких-либо правил удалять посты и блокировать пользователей

![GitHub top language](https://img.shields.io/github/languages/top/Ar-th-ur/vitrix)
![GitHub](https://img.shields.io/github/license/Ar-th-ur/vitrix)
![GitHub Repo stars](https://img.shields.io/github/stars/Ar-th-ur/vitrix)
![GitHub issues](https://img.shields.io/github/issues/Ar-th-ur/vitrix)

![Logo](./docs/wall.jpeg)

## Environments
`ADMIN_USERNAME` - логин админа (default: admin)

`ADMIN_PASSWORD` - пароль админа (default: 1234)

## Deployment
```
docker pull -e ADMIN_USERNAME=admin -e ADMIN_PASSWORD=1234 vitrix
```


