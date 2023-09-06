@import hexlet.code.pages.manager.AllUrlsPage
@import hexlet.code.model.Url
@param AllUrlsPage page

@template.layout.index(
page = page,
title = "All Urls",
content = @`
    <section>
        <div class="container-lg mt-5">
            <h1>Сайты</h1>
            <table class="table table-bordered table-hover mt-3">
                <thead>
                <tr>
                    <th class="col-1">ID</th>
                    <th>Имя</th>
                    <th class="col-2">Последняя проверка</th>
                    <th class="col-1">Код ответа</th>
                </tr>
                </thead>
                <tbody>
                @if(page.getUrls() != null)
                    @for(Url url : page.getUrls())
                        <tr>
                            <td>${url.getId()}</td>
                            <td>
                                <a href="/urls/${url.getId()}">${url.getName()}</a>
                            </td>
                            <td></td>
                            <td></td>
                        </tr>
                    @endfor
                @endif
                </tbody>
            </table>
            @if(page.getUrls() != null && page.getUrls().size() > 5)
                <nav aria-label="Page navigation">
                    <ul class="pagination justify-content-center mt-5">
                        @if(page.getPageCount() != 1)
                            <li class="page-item disabled">
                                <a class="page-link" href="/urls?page=${page.getPageCount() - 1}">Previous</a>
                            </li>
                        @endif
                        <li class="page-item active">
                            ${page.getPageCount()}
                        </li>
                        <li class="page-item disabled">
                            <a class="page-link" href="/urls?page=${page.getPageCount() + 1}">Next</a>
                        </li>
                    </ul>
                </nav>
            @endif
        </div>
    </section>
`
)
