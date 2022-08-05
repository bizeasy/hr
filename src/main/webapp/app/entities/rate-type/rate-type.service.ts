import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRateType } from 'app/shared/model/rate-type.model';

type EntityResponseType = HttpResponse<IRateType>;
type EntityArrayResponseType = HttpResponse<IRateType[]>;

@Injectable({ providedIn: 'root' })
export class RateTypeService {
  public resourceUrl = SERVER_API_URL + 'api/rate-types';

  constructor(protected http: HttpClient) {}

  create(rateType: IRateType): Observable<EntityResponseType> {
    return this.http.post<IRateType>(this.resourceUrl, rateType, { observe: 'response' });
  }

  update(rateType: IRateType): Observable<EntityResponseType> {
    return this.http.put<IRateType>(this.resourceUrl, rateType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRateType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRateType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
