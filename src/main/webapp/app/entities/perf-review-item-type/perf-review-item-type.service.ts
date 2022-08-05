import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPerfReviewItemType } from 'app/shared/model/perf-review-item-type.model';

type EntityResponseType = HttpResponse<IPerfReviewItemType>;
type EntityArrayResponseType = HttpResponse<IPerfReviewItemType[]>;

@Injectable({ providedIn: 'root' })
export class PerfReviewItemTypeService {
  public resourceUrl = SERVER_API_URL + 'api/perf-review-item-types';

  constructor(protected http: HttpClient) {}

  create(perfReviewItemType: IPerfReviewItemType): Observable<EntityResponseType> {
    return this.http.post<IPerfReviewItemType>(this.resourceUrl, perfReviewItemType, { observe: 'response' });
  }

  update(perfReviewItemType: IPerfReviewItemType): Observable<EntityResponseType> {
    return this.http.put<IPerfReviewItemType>(this.resourceUrl, perfReviewItemType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPerfReviewItemType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPerfReviewItemType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
