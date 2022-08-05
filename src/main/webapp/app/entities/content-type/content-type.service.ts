import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IContentType } from 'app/shared/model/content-type.model';

type EntityResponseType = HttpResponse<IContentType>;
type EntityArrayResponseType = HttpResponse<IContentType[]>;

@Injectable({ providedIn: 'root' })
export class ContentTypeService {
  public resourceUrl = SERVER_API_URL + 'api/content-types';

  constructor(protected http: HttpClient) {}

  create(contentType: IContentType): Observable<EntityResponseType> {
    return this.http.post<IContentType>(this.resourceUrl, contentType, { observe: 'response' });
  }

  update(contentType: IContentType): Observable<EntityResponseType> {
    return this.http.put<IContentType>(this.resourceUrl, contentType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IContentType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IContentType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
