import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISalesChannel } from 'app/shared/model/sales-channel.model';

type EntityResponseType = HttpResponse<ISalesChannel>;
type EntityArrayResponseType = HttpResponse<ISalesChannel[]>;

@Injectable({ providedIn: 'root' })
export class SalesChannelService {
  public resourceUrl = SERVER_API_URL + 'api/sales-channels';

  constructor(protected http: HttpClient) {}

  create(salesChannel: ISalesChannel): Observable<EntityResponseType> {
    return this.http.post<ISalesChannel>(this.resourceUrl, salesChannel, { observe: 'response' });
  }

  update(salesChannel: ISalesChannel): Observable<EntityResponseType> {
    return this.http.put<ISalesChannel>(this.resourceUrl, salesChannel, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISalesChannel>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISalesChannel[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
