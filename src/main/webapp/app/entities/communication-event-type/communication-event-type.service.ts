import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICommunicationEventType } from 'app/shared/model/communication-event-type.model';

type EntityResponseType = HttpResponse<ICommunicationEventType>;
type EntityArrayResponseType = HttpResponse<ICommunicationEventType[]>;

@Injectable({ providedIn: 'root' })
export class CommunicationEventTypeService {
  public resourceUrl = SERVER_API_URL + 'api/communication-event-types';

  constructor(protected http: HttpClient) {}

  create(communicationEventType: ICommunicationEventType): Observable<EntityResponseType> {
    return this.http.post<ICommunicationEventType>(this.resourceUrl, communicationEventType, { observe: 'response' });
  }

  update(communicationEventType: ICommunicationEventType): Observable<EntityResponseType> {
    return this.http.put<ICommunicationEventType>(this.resourceUrl, communicationEventType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICommunicationEventType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICommunicationEventType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
