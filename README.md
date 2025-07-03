# Tarefas

Aplicativo Android para gerenciamento de tarefas desenvolvido em Kotlin, utilizando arquitetura MVVM, Room Database e Navigation Component.

## Descrição

O **Tarefas** é um app simples de lista de tarefas (To-Do list) que permite ao usuário criar, editar, excluir e favoritar tarefas. O projeto foi desenvolvido com foco em boas práticas de arquitetura Android, persistência local com Room, e navegação entre telas com Navigation Component.

## Funcionalidades

- **Adicionar/Editar tarefa**: Crie ou edite tarefas com título e descrição.
- **Excluir tarefa**: Remova tarefas existentes da lista.
- **Favoritar tarefa**: Marque/desmarque tarefas como favoritas.
- **Busca de tarefas**: Filtre tarefas pelo título em tempo real.
- **Ordenação**: Ordene as tarefas por data de criação ou alfabeticamente.
- **Persistência local**: Todas as tarefas são armazenadas localmente usando Room.

## Estrutura do Projeto

- `app/src/main/java/com/example/tarefas/data/`: Classes de dados e acesso ao banco (Room).
- `app/src/main/java/com/example/tarefas/ui/`: Fragments e lógica de interface.
- `app/src/main/java/com/example/tarefas/ui/adapters/`: Adapters do RecyclerView.

## Principais Tecnologias

- **Kotlin**
- **Room Database**
- **Android Jetpack (Navigation, LiveData, ViewModel)**
- **RecyclerView**
- **Material Components**

## Como rodar o projeto

1. Clone o repositório:
   ```bash
   git clone https://github.com/Cesarkwy/Tarefas.git
   ```
2. Abra o projeto no Android Studio.
3. Sincronize as dependências com o Gradle.
4. Execute o app em um emulador ou dispositivo Android.



---

> _Resultados baseados em análise parcial do código-fonte. Veja mais detalhes ou arquivos no [GitHub Code Search](https://github.com/Cesarkwy/Tarefas/search?l=Kotlin)._
