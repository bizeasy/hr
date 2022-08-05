import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUom } from 'app/shared/model/uom.model';

type EntityResponseType = HttpResponse<IUom>;
type EntityArrayResponseType = HttpResponse<IUom[]>;

@Injectable({ providedIn: 'root' })
export class UomService {
  public resourceUrl = SERVER_API_URL + 'api/uoms';

  constructor(protected http: HttpClient) {}

  create(uom: IUom): Observable<EntityResponseType> {
    return this.http.post<IUom>(this.resourceUrl, uom, { observe: 'response' });
  }

  update(uom: IUom): Observable<EntityResponseType> {
    return this.http.put<IUom>(this.resourceUrl, uom, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUom>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUom[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
