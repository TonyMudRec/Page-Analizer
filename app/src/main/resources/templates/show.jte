@import hexlet.code.pages.manager.UrlPage
@import hexlet.code.model.Url
@param UrlPage page

@template.layout.index(
page = page,
title = "All Urls",
content = @`
<section>
    <div class="container-lg mt-5">
        <h1>Сайт ${page.getName()}</h1>

        <table class="table table-bordered table-hover mt-3">
            <tbody>
            <tr>
                <td>ID</td>
                <td>${page.getId()}</td>
            </tr>
            <tr>
                <td>Имя</td>
                <td>${page.getName()}</td>
            </tr>
            <tr>
                <td>Дата создания</td>
                <td>${page.getCreatedAt().toString()}</td>
            </tr>
            </tbody>
        </table>

        <h2 class="mt-5">Проверки</h2>
        <form method="post" action="/urls/${page.getId()}/checks">
            <button type="submit" class="btn btn-primary">Запустить проверку</button>
        </form>

        <table class="table table-bordered table-hover mt-3">
            <thead>
            <th class="col-1">ID</th>
            <th class="col-1">Код ответа</th>
            <th>title</th>
            <th>h1</th>
            <th>description</th>
            <th class="col-2">Дата проверки</th>
            </thead>
            <tbody>
<%--            @if()--%>
<%--                --%>
<%--            @endif--%>
            </tbody>
        </table>
    </div>
</section>
`
)
