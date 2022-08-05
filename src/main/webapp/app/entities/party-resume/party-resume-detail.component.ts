import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPartyResume } from 'app/shared/model/party-resume.model';

@Component({
  selector: 'sys-party-resume-detail',
  templateUrl: './party-resume-detail.component.html',
})
export class PartyResumeDetailComponent implements OnInit {
  partyResume: IPartyResume | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partyResume }) => (this.partyResume = partyResume));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
