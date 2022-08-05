import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICommunicationEvent } from 'app/shared/model/communication-event.model';

type EntityResponseType = HttpResponse<ICommunicationEvent>;
type EntityArrayResponseType = HttpResponse<ICommunicationEvent[]>;

@Injectable({ providedIn: 'root' })
export class CommunicationEventService {
  public resourceUrl = SERVER_API_URL + 'api/communication-events';

  constructor(protected http: HttpClient) {}

  create(communicationEvent: ICommunicationEvent): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(communicationEvent);
    return this.http
      .post<ICommunicationEvent>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(communicationEvent: ICommunicationEvent): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(communicationEvent);
    return this.http
      .put<ICommunicationEvent>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICommunicationEvent>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICommunicationEvent[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(communicationEvent: ICommunicationEvent): ICommunicationEvent {
    const copy: ICommunicationEvent = Object.assign({}, communicationEvent, {
      entryDate: communicationEvent.entryDate && communicationEvent.entryDate.isValid() ? communicationEvent.entryDate.toJSON() : undefined,
      dateStarted:
        communicationEvent.dateStarted && communicationEvent.dateStarted.isValid() ? communicationEvent.dateStarted.toJSON() : undefined,
      dateEnded: communicationEvent.dateEnded && communicationEvent.dateEnded.isValid() ? communicationEvent.dateEnded.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.entryDate = res.body.entryDate ? moment(res.body.entryDate) : undefined;
      res.body.dateStarted = res.body.dateStarted ? moment(res.body.dateStarted) : undefined;
      res.body.dateEnded = res.body.dateEnded ? moment(res.body.dateEnded) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((communicationEvent: ICommunicationEvent) => {
        communicationEvent.entryDate = communicationEvent.entryDate ? moment(communicationEvent.entryDate) : undefined;
        communicationEvent.dateStarted = communicationEvent.dateStarted ? moment(communicationEvent.dateStarted) : undefined;
        communicationEvent.dateEnded = communicationEvent.dateEnded ? moment(communicationEvent.dateEnded) : undefined;
      });
    }
    return res;
  }
}
