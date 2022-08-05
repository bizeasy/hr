import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPerfReviewItem } from 'app/shared/model/perf-review-item.model';

type EntityResponseType = HttpResponse<IPerfReviewItem>;
type EntityArrayResponseType = HttpResponse<IPerfReviewItem[]>;

@Injectable({ providedIn: 'root' })
export class PerfReviewItemService {
  public resourceUrl = SERVER_API_URL + 'api/perf-review-items';

  constructor(protected http: HttpClient) {}

  create(perfReviewItem: IPerfReviewItem): Observable<EntityResponseType> {
    return this.http.post<IPerfReviewItem>(this.resourceUrl, perfReviewItem, { observe: 'response' });
  }

  update(perfReviewItem: IPerfReviewItem): Observable<EntityResponseType> {
    return this.http.put<IPerfReviewItem>(this.resourceUrl, perfReviewItem, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPerfReviewItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPerfReviewItem[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
