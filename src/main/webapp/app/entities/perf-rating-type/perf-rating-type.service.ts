import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPerfRatingType } from 'app/shared/model/perf-rating-type.model';

type EntityResponseType = HttpResponse<IPerfRatingType>;
type EntityArrayResponseType = HttpResponse<IPerfRatingType[]>;

@Injectable({ providedIn: 'root' })
export class PerfRatingTypeService {
  public resourceUrl = SERVER_API_URL + 'api/perf-rating-types';

  constructor(protected http: HttpClient) {}

  create(perfRatingType: IPerfRatingType): Observable<EntityResponseType> {
    return this.http.post<IPerfRatingType>(this.resourceUrl, perfRatingType, { observe: 'response' });
  }

  update(perfRatingType: IPerfRatingType): Observable<EntityResponseType> {
    return this.http.put<IPerfRatingType>(this.resourceUrl, perfRatingType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPerfRatingType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPerfRatingType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
