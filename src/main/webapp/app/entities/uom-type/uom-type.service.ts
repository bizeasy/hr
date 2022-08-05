import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUomType } from 'app/shared/model/uom-type.model';

type EntityResponseType = HttpResponse<IUomType>;
type EntityArrayResponseType = HttpResponse<IUomType[]>;

@Injectable({ providedIn: 'root' })
export class UomTypeService {
  public resourceUrl = SERVER_API_URL + 'api/uom-types';

  constructor(protected http: HttpClient) {}

  create(uomType: IUomType): Observable<EntityResponseType> {
    return this.http.post<IUomType>(this.resourceUrl, uomType, { observe: 'response' });
  }

  update(uomType: IUomType): Observable<EntityResponseType> {
    return this.http.put<IUomType>(this.resourceUrl, uomType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUomType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUomType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
