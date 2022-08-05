import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IKeywordType } from 'app/shared/model/keyword-type.model';

type EntityResponseType = HttpResponse<IKeywordType>;
type EntityArrayResponseType = HttpResponse<IKeywordType[]>;

@Injectable({ providedIn: 'root' })
export class KeywordTypeService {
  public resourceUrl = SERVER_API_URL + 'api/keyword-types';

  constructor(protected http: HttpClient) {}

  create(keywordType: IKeywordType): Observable<EntityResponseType> {
    return this.http.post<IKeywordType>(this.resourceUrl, keywordType, { observe: 'response' });
  }

  update(keywordType: IKeywordType): Observable<EntityResponseType> {
    return this.http.put<IKeywordType>(this.resourceUrl, keywordType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IKeywordType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IKeywordType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
