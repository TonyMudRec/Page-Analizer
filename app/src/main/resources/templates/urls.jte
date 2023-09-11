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
            @if(page.getListSize() > 5)
                <nav aria-label="Page navigation example">
                    <ul class="pagination justify-content-center">
                        <li class="page-item
                        @if(page.getCurrentPage() == 1)
                         disabled
                        @endif
                            ">
                            <a class="page-link" href="/urls?pageNumber=${page.getCurrentPage() - 1}">Previous</a>
                        </li>
                        <li class="page-item active">
                            <a class="page-link" href="/urls?pageNumber=${page.getCurrentPage()}">
                                ${page.getCurrentPage()}
                            </a>
                        </li>
                        <li class="page-item
                        @if(page.getCurrentPage() * 5 >= page.getListSize())
                         disabled
                        @endif
                            ">
                            <a class="page-link" href="/urls?pageNumber=${page.getCurrentPage() + 1}">Next</a>
                        </li>
                    </ul>
                </nav>
            @endif
        </div>
    </section>
`
)
